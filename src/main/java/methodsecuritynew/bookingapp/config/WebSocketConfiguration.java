//package methodsecuritynew.bookingapp.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.*;
//
//@Configuration
//
//
//@EnableWebSocketMessageBroker
//public class WebSocketConfiguration implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(new BookingWebSocketHandler(), "/ws-bookings").setAllowedOrigins("http://localhost:9000");
//    }
//}
