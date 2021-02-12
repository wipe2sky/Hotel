package task3.task3;

public class Solution {
    public static void main(String[] args) {
        IProduct tank = new Tank();
        IAssemblyLine assemblyLine = new AssemblyLine();
        assemblyLine.assemblyProduct(tank);
        System.out.println("Work done!");
    }
}
