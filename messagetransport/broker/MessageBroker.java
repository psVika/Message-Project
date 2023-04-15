package by.messagetransport.broker;

import by.messagetransport.model.Message;

import java.util.ArrayDeque;
import java.util.Queue;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class MessageBroker {
    private final Queue<Message> messagesToBeConsumed;//������� ����� ���������
    private final int maxStoredMessages;//���-�� ����������� ���������� ��������� ���������� � MessageBroker

    public MessageBroker(final int maxStoredMessages) {
        this.messagesToBeConsumed = new ArrayDeque<>(maxStoredMessages);//������� � �������� ������ maxStoredMessages
        this.maxStoredMessages = maxStoredMessages;
    }

    public synchronized void produce(final Message message){//����� ������ ��������� � ���� �������
        try {
            while (this.messagesToBeConsumed.size() >= this.maxStoredMessages){//������ ������� ����� ����������� ����������� ������� �������
                super.wait();
            }
            this.messagesToBeConsumed.add(message);//�������� ��������� � �������
            super.notify(); //����� ����������, ��� ������� ��� �� ������
        }
        catch(final InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized Message consume() {  //�����, ������� ���������� ��������� �� ������� � ������� ���
        try {
            while (this.messagesToBeConsumed.isEmpty()) { //���������� � ���, ��� ������� �� �����. ���� ��� ����� ������ � ������ �� ������.
                super.wait();//���� ������� �����,�� ����������� �������. ����� produceThread ��� �������� ��������� � �������, � �� ����� ��� ���������.
            }
            final Message consumedMessage = this.messagesToBeConsumed.poll();//��������� ���������� ���������
            super.notify();//����� ��������� produceThread ��� �� Broker ���� ����� ���������
            return consumedMessage;
        } catch (final InterruptedException interruptedException) {
            Thread.currentThread().interrupted();//��� ��� �� �� ����� �������� ������, ���� ��� ������� ����������� �� �����
            throw new RuntimeException(interruptedException);

        }
    }
}
