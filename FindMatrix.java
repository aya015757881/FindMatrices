package aya;

import java.util.Random;
import java.util.Scanner;

public class FindMatrix {
	
	int count;//记录满足要求的矩阵的个数
	int rowCount, columnCount;//2个整型变量，分别用来保存矩阵的行数与列数
	int[] rowSums, columnSums;//2个一维数组，分别用来保存矩阵的行和与列和
	int[][] matrix;//1个二维数组，和矩阵对应
	int[][] positions;//1个二维数组，用来保存矩阵中"1"的下标
	
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();
	
	public static void main(String[] args) {
		
		new FindMatrix().go();
	}
	
	/*
	 * 开始
	 */
	void go(){
		
		while(true){
			
			count = 0;
			setMatrixParameters();
			
			do{
				setRandomTestCase();
				combine(0, 0, 0, columnCount - rowSums[0]);
			}while(count == 0);
			
			printConditions();
		}
	}
	
	/*
	 * 为矩阵设定大小
	 */
	void setMatrixParameters(){
		
		//用户设定矩阵的行数
		System.out.print("请输入矩阵的行数：");
		rowCount = scan.nextInt();
		
		//用户设定矩阵的列数
		System.out.print("请输入矩阵的列数：");
		columnCount = scan.nextInt();
	}
	
	/*
	 * 用随机数生成随机的行和与列和
	 */
	void setRandomTestCase(){
	
		//定义2个数组，分别用来保存行和与列和
		rowSums = new int[rowCount];
		columnSums = new int[columnCount];
		
		//生成随机的行和
		for(int i = 0; i < rowCount; i++)
			rowSums[i] = rand.nextInt(columnCount + 1);
		
		//生成随机的列和
		for(int i = 0; i < columnCount; i++)
			columnSums[i] = rand.nextInt(rowCount + 1);
		
		positions = new int[rowCount][];
		
		for(int i = 0; i < rowCount; i++)
			positions[i] = new int[rowSums[i]];
		
		matrix = new int[rowCount][columnCount];
	}
	
	/*
	 * 把矩阵中被赋值的当前行清空
	 */
	void clear(int row){
		
		for(int i = 0; i < columnCount; i++)
			if(matrix[row][i] == 1)
				matrix[row][i] = 0;
	}
	
	/*
	 * 用组合器遍历所有符合行和条件的矩阵
	 */
	void combine(int row, int index, int start, int end){
		
		if(rowSums[row] == 0){
			if(isLegalRow(row))
				if(row < rowCount - 1)
					combine(row + 1, 0, 0, columnCount - rowSums[row + 1]);
				else
					printMatrix();
		}else
			for(positions[row][index] = start; positions[row][index] <= end; positions[row][index]++)
				if(index < rowSums[row] - 1)
					combine(row, index + 1, positions[row][index] + 1, end + 1);
				else if(!isLegalRow(row))
					continue;
				else if(row < rowCount - 1)
					combine(row + 1, 0, 0, columnCount - rowSums[row + 1]);
				else
					printMatrix();
	}
	
	/*
	 * 判断该行的组合情况是否违反列和条件
	 */
	boolean isLegalRow(int row){
		
		clear(row);
		
		for(int i = 0; i < rowSums[row]; i++)
			matrix[row][positions[row][i]] = 1;
		
		for(int i = 0; i < columnCount; i++){
			
			int sum = 0;
			
			for(int j = 0; j <= row; j++)
				sum += matrix[j][i];
			
			if(sum > columnSums[i] || sum + rowCount - row - 1 < columnSums[i])
				return false;
		}
		
		return true;
	}
	
	/*
	 * 打印符合条件的矩阵
	 */
	void printMatrix(){
		
		System.out.println( "第" + ++count + "个矩阵：");
		
		for(int i = 0; i < rowCount; i++){
			
			for(int j = 0; j < columnCount; j++)
				System.out.print(matrix[i][j] + " ");
			
			System.out.println();
		}
		
		System.out.println();
	}

	/*
	 * 显示行和与列和的条件
	 */
	void printConditions(){
		 
		System.out.print("行和：");
		for(int a : rowSums)
			System.out.print(a + " ");
		System.out.println();
		
		System.out.print("列和：");
		for(int a : columnSums)
			System.out.print(a + " ");
		System.out.println("\n");
		
		System.out.println("满足上述要求的矩阵一共有以上" + count + "个（行和与列和是随机生成的条件）\n\n");
	}
}
