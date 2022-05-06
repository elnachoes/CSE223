class States
{
    private int result = 0;
    private Boolean isStateAddMode = true;
    public int result() { return result; }
    public void process(int n) { if (isStateAddMode) result += n; else result *= n; }
    public void doAdd() { isStateAddMode = true; }
    public void doMul() { isStateAddMode = false; }
}