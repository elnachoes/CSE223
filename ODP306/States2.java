public class States2 {
    int result = 0;
    String state = "add";

    int result() { return result; }
    
    void process(int n) {
        if (state.compareTo("add") == 0) {
            result += n;
        }
        if (state.compareTo("sub") == 0) {
            result -= n;
        }
        if (state.compareTo("mul") == 0) {
            result *= n;
        }
        if (state.compareTo("div") == 0) {
            result /= n;
        }
    }

    void doOp(String cmd) {
        if (cmd.compareTo("add".toLowerCase()) == 0 ||
            cmd.compareTo("sub".toLowerCase()) == 0 ||
            cmd.compareTo("mul".toLowerCase()) == 0 ||
            cmd.compareTo("div".toLowerCase()) == 0) {
            state = cmd.toLowerCase();
        }
    }
}