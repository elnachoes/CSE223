import java.util.Scanner;
class AddClass
{
    private int RunningSum = 0;
    private Scanner scanner = new Scanner(System.in);
    public void read() { RunningSum += scanner.nextInt(); }
    public int sum(){ return RunningSum; }
}