package by.messagetransport.producer;

import by.messagetransport.broker.MessageBroker;
import by.messagetransport.model.Message;

import java.util.concurrent.TimeUnit;

public final class MessageProducingTask implements Runnable {
    private static final String MESSAGE_OF_MESSAGE_IS_PRODUCED = "Message '%s' is produced.\n";
    private static final int SECONDS_DURATION_TO_SLEEP_BEFORE_PRODUCING =1;

    private final MessageBroker massageBroker;
    private final MessageFactory massageFactory;

    public MessageProducingTask(final MessageBroker messageBroker) {
        this.massageBroker = messageBroker;
        this.massageFactory = new MessageFactory();
    }

    @Override
    public void run() { //���� ������� ����� �� �������, ����� ����������� ��������� � ������� MessageFactory
        try{
            while(!Thread.currentThread().isInterrupted()){
                final Message producedMessage = this.massageFactory.create();
                TimeUnit.SECONDS.sleep(SECONDS_DURATION_TO_SLEEP_BEFORE_PRODUCING);
                this.massageBroker.produce(producedMessage);
                System.out.printf(MESSAGE_OF_MESSAGE_IS_PRODUCED,producedMessage);//��������, ��� ��������� ���� �������� � massageBroker
            }
        }
        catch (final InterruptedException inerruptedException){
            Thread.currentThread().interrupt();
        }
    }

    private static final class MessageFactory { //������� ���������
        private static final int INITIAL_NEXT_MESSAGE_INDEX = 1;//����������� ��������
        private static final String TEMPLATE_CREATED_MESSAGE_DATA = "Message#%d";

        private int nextMessageIndex;

        public MessageFactory(){
            this.nextMessageIndex = INITIAL_NEXT_MESSAGE_INDEX;
        }
        public Message create(){    //����� ��������� ���������
            return new Message(String.format(TEMPLATE_CREATED_MESSAGE_DATA, this.nextMessageIndex++));
        }
    }
}
