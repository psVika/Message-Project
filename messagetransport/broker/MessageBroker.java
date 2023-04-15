package by.messagetransport.broker;

import by.messagetransport.model.Message;

import java.util.ArrayDeque;
import java.util.Queue;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class MessageBroker {
    private final Queue<Message> messagesToBeConsumed;//очередь наших сообщений
    private final int maxStoredMessages;//кол-во максимально допустимых сообщений хранящихся в MessageBroker

    public MessageBroker(final int maxStoredMessages) {
        this.messagesToBeConsumed = new ArrayDeque<>(maxStoredMessages);//очередь с ёмкостью равной maxStoredMessages
        this.maxStoredMessages = maxStoredMessages;
    }

    public synchronized void produce(final Message message){//метод кладет сообщения в нашу очередь
        try {
            while (this.messagesToBeConsumed.size() >= this.maxStoredMessages){//размер очереди равен максимально допустимому размеру очереди
                super.wait();
            }
            this.messagesToBeConsumed.add(message);//положили сообщение в очередь
            super.notify(); //чтобы уведомлять, что очередь уже не пустая
        }
        catch(final InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized Message consume() {  //Метод, который возвращает сообщение из очереди и удаляет его
        try {
            while (this.messagesToBeConsumed.isEmpty()) { //убеждаемся в том, что очередь не пуста. Пока она пуста ничего в методе не делаем.
                super.wait();//пока очередь пуста,мы освобождаем монитор. Чтобы produceThread мог положить сообщение в очередь, и мы могли его потребить.
            }
            final Message consumedMessage = this.messagesToBeConsumed.poll();//сохраняем полученное сообщение
            super.notify();//чтобы уведомить produceThread что из Broker было взято сообщение
            return consumedMessage;
        } catch (final InterruptedException interruptedException) {
            Thread.currentThread().interrupted();//так как мы не будем прервать потоки, этот код никогда выполняться не будет
            throw new RuntimeException(interruptedException);

        }
    }
}
