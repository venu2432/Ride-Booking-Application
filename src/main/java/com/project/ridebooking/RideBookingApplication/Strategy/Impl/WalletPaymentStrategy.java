package com.project.ridebooking.RideBookingApplication.Strategy.Impl;

import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.PaymentStatus;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.TransactionMethod;
import com.project.ridebooking.RideBookingApplication.Entity.Payment;
import com.project.ridebooking.RideBookingApplication.Entity.Rider;
import com.project.ridebooking.RideBookingApplication.Repository.PaymentRepository;
import com.project.ridebooking.RideBookingApplication.Service.PaymentService;
import com.project.ridebooking.RideBookingApplication.Service.WalletService;
import com.project.ridebooking.RideBookingApplication.Strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy
{
    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(),
                payment.getAmount(), null, payment.getRide(), TransactionMethod.RIDE);

        Double driverAmount = payment.getAmount() * (1 - PLATFORM_COMMISION);

        walletService.addMoneyToWallet(driver.getUser(),
                driverAmount, null, payment.getRide(), TransactionMethod.RIDE);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
