/*
��������� � ������� Message Producer ����� ��������� � ���������� ��������� ��������� � Message Broker.
Message Broker ����� ������ ������������ ��� ���������, ������� ��� ������� Message Producer.
Message Consumer ����� ���������� ��������� �� Message Broker � ������� �� ���� �� ���������, ������� �� ��� ������.
������������ ���������� ���������, ������� ����� ������� � Message Broker ����� 5.
 */
package by.messagetransport;

import by.messagetransport.broker.MessageBroker;
import by.messagetransport.consumer.MessageConsumingTask;
import by.messagetransport.producer.MessageProducingTask;

public class Main {
    public static void main(String[] args) {
        final int brokerMaxStoredMessages = 5; //������ ������� �������� 5 ���������
        final MessageBroker messageBroker = new MessageBroker(brokerMaxStoredMessages); //���������� ��� ������

        final Thread producingThread = new Thread(new MessageProducingTask(messageBroker)); //�����, ������������ ��������� � ������
        final Thread consumingThread = new Thread(new MessageConsumingTask(messageBroker)); //����� ������������ ��������� �� �������


        producingThread.start();
        consumingThread.start();

    }
}