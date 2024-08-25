package com.project.ridebooking.RideBookingApplication.Strategy.Impl;

import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.PaymentStatus;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.TransactionMethod;
import com.project.ridebooking.RideBookingApplication.Entity.Payment;
import com.project.ridebooking.RideBookingApplication.Repository.PaymentRepository;
import com.project.ridebooking.RideBookingApplication.Service.PaymentService;
import com.project.ridebooking.RideBookingApplication.Service.WalletService;
import com.project.ridebooking.RideBookingApplication.Strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;


    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        Double platformCommission = payment.getAmount() * PLATFORM_COMMISION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission, null,
                payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
