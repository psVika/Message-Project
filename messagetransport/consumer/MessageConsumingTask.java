package by.messagetransport.consumer;

import by.messagetransport.broker.MessageBroker;
import by.messagetransport.model.Message;
import java.util.concurrent.TimeUnit;

public final class MessageConsumingTask implements Runnable {
    private static final int SECONDS_DURATION_TO_SLEEP_BEFORE_CONSUMING = 1;
    private static final String TEMPLATE_MESSAGE_OF_MESSAGE_IS_CONSUMING = "Message '%s' is consumed. \n";

    private final MessageBroker messageBroker;

    public MessageConsumingTask(final MessageBroker messageBroker){
        this.messageBroker = messageBroker;
        
    }

    @Override
    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted()) { //пока текущий поток не прерван, мы будем потреблять сообщения из messageBroker
                TimeUnit.SECONDS.sleep(SECONDS_DURATION_TO_SLEEP_BEFORE_CONSUMING);
                final Message consumedMessage = this.messageBroker.consume();//из Broker получаем сообщение
                System.out.printf(TEMPLATE_MESSAGE_OF_MESSAGE_IS_CONSUMING, consumedMessage);
            }
        }
        catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupted();
        }
    }
}
