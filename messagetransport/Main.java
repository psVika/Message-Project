/*
Программа в которой Message Producer будет создавать и передавать некоторые сообщения в Message Broker.
Message Broker будет складировать сообщения, которые ему передал Message Producer.
Message Consumer будет потреблять сообщения из Message Broker и удалять из него те сообщения, которые он уже принял.
Максимальное количество сообщений, которые могут храниться в Message Broker равно 5.
 */
package by.messagetransport;

import by.messagetransport.broker.MessageBroker;
import by.messagetransport.consumer.MessageConsumingTask;
import by.messagetransport.producer.MessageProducingTask;

public class Main {
    public static void main(String[] args) {
        final int brokerMaxStoredMessages = 5; //брокер выводит максимум 5 сообщений
        final MessageBroker messageBroker = new MessageBroker(brokerMaxStoredMessages); //определяем сам брокер

        final Thread producingThread = new Thread(new MessageProducingTask(messageBroker)); //поток, записывающий сообщения в брокер
        final Thread consumingThread = new Thread(new MessageConsumingTask(messageBroker)); //поток потребляющий сообщения из брокера


        producingThread.start();
        consumingThread.start();

    }
}
