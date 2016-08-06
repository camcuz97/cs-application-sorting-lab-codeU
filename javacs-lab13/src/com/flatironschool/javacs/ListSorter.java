/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
		if(list.size() <= 1){
			return list;
		}
		List<T> left = mergeSort(new LinkedList<T>(list.subList(0, list.size()/2)), comparator);
		List<T> right = mergeSort(new LinkedList<T>(list.subList(list.size()/2, list.size())), comparator);
		return mergeSortHelper(left, right, comparator);
	}
	
	private List<T> mergeSortHelper(List<T> left, List<T> right, Comparator<T> comparator){
		List<T> retList = new LinkedList<T>();
		int compare = 0;
		while(!left.isEmpty() && !right.isEmpty()){
			compare = comparator.compare(left.get(0), right.get(0));
			if(compare < 0){
				retList.add(left.remove(0));
			}
			else{
				retList.add(right.remove(0));
			}
		}
		while(!left.isEmpty()){
			retList.add(left.remove(0));
		}
		while(!right.isEmpty()){
			retList.add(right.remove(0));
		}
		return retList;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> tempList = new PriorityQueue<T>();
		for(int i = 0; i < list.size(); i++){
			tempList.offer(list.get(i));
		}
		list.clear();
		while(!tempList.isEmpty()){
			list.add(tempList.poll());
		}
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> pqList = new PriorityQueue<T>(list.size(), comparator);
		for(T item: list){
			if(pqList.size() < k){
				pqList.offer(item);
			}
			else{
				int comp = comparator.compare(item, pqList.peek());
				if(comp > 0){
					pqList.poll();
					pqList.offer(item);
				}
			}
		}
		List<T> retList = new ArrayList<T>();
		while(!pqList.isEmpty()){
			retList.add(pqList.poll());
		}
        return retList;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
