package textgen;

import java.util.AbstractList;

//@S WITH SENTINALS
/** A class that implements a doubly linked list 
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<E>();
		tail = new LLNode<E>();
		head.next = tail;
		tail.prev= head;
		
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		boolean flag = false;
		if(element!=null){
			tail.data = element;
			tail.next = new LLNode<E>();
			tail.next.prev = tail;
			tail = tail.next;
			size++;
			return !flag;
		}
		else
			throw new NullPointerException("null input not allowed");
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if(index<size && index>=0){
			int i =0;
			LLNode<E> temp = head.next;
			while(i<index && temp.next!=null){
				i++;
				temp = temp.next;
			}
			return temp.data;
		}
		else
			throw new IndexOutOfBoundsException("index out of bound");
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if(element!=null){
			if(index>=0 && (index-size)<2){
				if(index<size){
					if(this.get(index)!=null){
						LLNode<E> newNode = new LLNode<E>(element);
						//find the node before the node at index
						int i = 0;
						LLNode<E> temp = head.next;
						while(i<index-1){
							i++;
							temp = temp.next;
						}
						newNode.next = temp.next;
						temp.next.prev = newNode;
						temp.next = newNode;
						newNode.prev = temp;
						size++;
						
					}
					else{
						
						}
				}
				else {
					tail.data = element;
					tail.next = new LLNode<E>();
					tail.next.prev = tail;
					tail = tail.next;
					size++;
				}
			}
			else
				throw new IndexOutOfBoundsException();
		}
		else
			throw new NullPointerException();
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if(index>=0 && index<size){
			int i =0;
			LLNode<E> temp = head;
			// find the node before the node at position index
			while(i<index){
				i++;
				temp = temp.next;
			}
			E element = temp.next.data;
			temp.next.next.prev = temp;
			temp.next = temp.next.next;
			size--;
			return element;
		}
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if(index>=0 && index<size){
			if(this.get(index)!=null && element!=null){
				int i = 0;
				LLNode<E> temp = head.next;
				while(i<index && temp.next!=null){
					i++;
					temp = temp.next;
				}
				E output = temp.data;
				temp.data = element;
				return output;
			}
			else
				throw new NullPointerException();
		}
		else
			throw new IndexOutOfBoundsException();
		
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	public LLNode() 
	{
		this.data = null;
		this.prev = null;
		this.next = null;
	}

}
