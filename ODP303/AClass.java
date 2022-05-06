class AClass
{
    int[] data = new int[10];;
    public AClass() { for (int i = 0; i < data.length; i++) data[i] = 1; }
    public void set(int index, int value) { data[index] = value; }
    public int get(int index) { return data[index]; }
}