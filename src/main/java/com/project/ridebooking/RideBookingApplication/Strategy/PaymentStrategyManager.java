package com.project.ridebooking.RideBookingApplication.Strategy;


import com.project.ridebooking.RideBookingApplication.Entity.Enums.PaymentMethod;
import com.project.ridebooking.RideBookingApplication.Strategy.Impl.CashPaymentStrategy;
import com.project.ridebooking.RideBookingApplication.Strategy.Impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final CashPaymentStrategy cashPaymentStrategy;
    private final WalletPaymentStrategy walletPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod){
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
