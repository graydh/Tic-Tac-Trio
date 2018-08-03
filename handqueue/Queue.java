package handqueue;

import java.util.NoSuchElementException;

public class Queue<T> implements UnboundedQueueInterface<T> {
	
	//pointers to the rear and front Nodes
	protected Node<T> front;
	protected Node<T> rear;
	
	//size or number of elements in the Queue
	protected int size;
	
	
	public Queue() {		
            front = null;
            rear = null;
    }
	
	public Queue(Queue<T> other) {
			int nodestodouble = other.size();
			while(nodestodouble > 0) {
				T element = other.dequeue();
				this.enqueue(element);
				other.enqueue(element);
				nodestodouble = nodestodouble - 1;
				
			}
	}
	
	@Override
	public boolean isEmpty() {
        //return true if the size is zero, otherwise return false    
		return (size == 0);
	}

	@Override
	public int size() {
            //return the current size of the queue (int)
            return size;
	}

	@Override
	public void enqueue(T element) {
			Node<T> newNode = new Node<T>(element);
			if(rear == null) {
				front = newNode;
			}
			else {
				rear.setNext(newNode);
			}
			rear = newNode;
			size = size + 1;
	}

	@Override
	public T dequeue() throws NoSuchElementException {
			if(isEmpty()) {
				throw new NoSuchElementException("Cannot dequeue can empty queue!");
			}
			else {
				T element;
				element = front.getData();
				front = front.getNext();
				if(front == null) {
					rear = null;
			}
			size = size - 1;
			return element;
			}
	}

	@Override
	public T peek() throws NoSuchElementException {
            if(this.isEmpty()) {
            	throw new NoSuchElementException("Cannot peek an empty queue");
            }
            else {
            	return front.getData();
            }
	}

	@Override
	public UnboundedQueueInterface<T> reversed() {
            Queue<T> reversedQueue = new Queue<T>();
            Queue<T> originalQueue = new Queue<T>(this);
            LinkedStack<T> tempStack = new LinkedStack<T>();
            
            
            while(!originalQueue.isEmpty()) {
            	T element = originalQueue.dequeue();
            	tempStack.push(element);
            	//we call call some version of reversed
            	//originalQueue.reversed();
            	//then append the current element we are in to the queue
            	//reversedQueue.enqueue(element);
            }
            while(!tempStack.isEmpty()) {
            	reversedQueue.enqueue(tempStack.pop());
            }
          //then return the queue            
          return reversedQueue;
	}
}
