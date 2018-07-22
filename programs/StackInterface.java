public interface StackInterface {
    public boolean push(int data);
    public int pop();
    public boolean isFull();
    public int depth();
    public boolean isEmpty();
    public void sort(boolean isIncreasingOrder);
    public int peek();
    public int capacity();
}
