package com.ecommerce.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.OrderException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Order;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.response.PaymentLinkResponse;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api")
public class PaymentController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@PostMapping("/payments/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(
			@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt
			) throws RazorpayException, UserException, OrderException{
		
		Order order = orderService.findOrderById(orderId);
		try {
			RazorpayClient razorpay = new RazorpayClient("key", "merchantId");
			
			JSONObject paymentLinkRequest = new JSONObject();
			
			paymentLinkRequest.put("Amount", order.getTotalPrice()*100);
			paymentLinkRequest.put("currency", "INR");
			
			JSONObject customer = new JSONObject();
			customer.put("name", order.getUser().getFirstName());
			customer.put("contact", order.getUser().getMobile());
			customer.put("email", order.getUser().getEmail());
			
			paymentLinkRequest.put("customer", customer);
			
			JSONObject notify = new JSONObject();
			notify.put("SMS :", true);
			notify.put("email :", true);
			paymentLinkRequest.put("notify", notify);
			
			paymentLinkRequest.put("callback_url", "http://localhost:4200/payment-success?order_id="+order.getId());
			paymentLinkRequest.put("callback_method", "get");
			
			PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
			
			String paymentLinkId = payment.get("id");
			String paymentLinkUrl = payment.get("short_url");
			
			PaymentLinkResponse res = new PaymentLinkResponse();
			res.setPayment_link_id(paymentLinkId);
			res.setPayment_link_url(paymentLinkUrl);
			
			return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.CREATED);
			
		} catch (Exception e) {
			throw new RazorpayException(e.getMessage());
		}
		
	}
	
	@GetMapping("/payments")
	public ResponseEntity<ApiResponse> updatePayment(
			@RequestParam(name="payment_id") String paymentId,
			@RequestParam(name="order_id") Long orderId ) throws OrderException, RazorpayException{
		
		RazorpayClient razorpay = new RazorpayClient("key", "merchantId");
		Order order = orderService.findOrderById(orderId);
		
		try {
			Payment payment = razorpay.payments.fetch(paymentId);
			
			if(payment.get("status").equals("captured")) {
				order.setOrderStatus("PLACED");
				order.getPaymentDetails().setPaymentId(paymentId);
				order.getPaymentDetails().setRazorpayPaymentLinkStatus("COMPLETED");
				
				orderRepository.save(order);
			}
			
			ApiResponse res = new ApiResponse();
			res.setMessage("Your order has been placed");
			res.setStatus(true);
			
			return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
			
		} catch (Exception e) {
			throw new RazorpayException(e.getMessage());
		}

		
	}
}
