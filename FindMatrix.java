package aya;

import java.util.Random;
import java.util.Scanner;

public class FindMatrix {
	
	int count;//��¼����Ҫ��ľ���ĸ���
	int rowCount, columnCount;//2�����ͱ������ֱ�����������������������
	int[] rowSums, columnSums;//2��һά���飬�ֱ��������������к����к�
	int[][] matrix;//1����ά���飬�;����Ӧ
	int[][] positions;//1����ά���飬�������������"1"���±�
	
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();
	
	public static void main(String[] args) {
		
		new FindMatrix().go();
	}
	
	/*
	 * ��ʼ
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
	 * Ϊ�����趨��С
	 */
	void setMatrixParameters(){
		
		//�û��趨���������
		System.out.print("����������������");
		rowCount = scan.nextInt();
		
		//�û��趨���������
		System.out.print("����������������");
		columnCount = scan.nextInt();
	}
	
	/*
	 * �����������������к����к�
	 */
	void setRandomTestCase(){
	
		//����2�����飬�ֱ����������к����к�
		rowSums = new int[rowCount];
		columnSums = new int[columnCount];
		
		//����������к�
		for(int i = 0; i < rowCount; i++)
			rowSums[i] = rand.nextInt(columnCount + 1);
		
		//����������к�
		for(int i = 0; i < columnCount; i++)
			columnSums[i] = rand.nextInt(rowCount + 1);
		
		positions = new int[rowCount][];
		
		for(int i = 0; i < rowCount; i++)
			positions[i] = new int[rowSums[i]];
		
		matrix = new int[rowCount][];

		for(int i = 0; i < rowCount; i++)
			matrix[i] = new int[columnCount];
	}
	
	/*
	 * �Ѿ����б���ֵ�ĵ�ǰ�����
	 */
	void clear(int row){
		
		for(int i = 0; i < columnCount; i++)
			if(matrix[row][i] == 1)
				matrix[row][i] = 0;
	}
	
	/*
	 * ��������������з����к������ľ���
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
	 * �жϸ��е��������Ƿ�Υ���к�����
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
	 * ��ӡ���������ľ���
	 */
	void printMatrix(){
		
		System.out.println( "��" + ++count + "������");
		
		for(int i = 0; i < rowCount; i++){
			
			for(int j = 0; j < columnCount; j++)
				System.out.print(matrix[i][j] + " ");
			
			System.out.println();
		}
		
		System.out.println();
	}

	/*
	 * ��ʾ�к����к͵�����
	 */
	void printConditions(){
		 
		System.out.print("�кͣ�");
		for(int a : rowSums)
			System.out.print(a + " ");
		System.out.println();
		
		System.out.print("�кͣ�");
		for(int a : columnSums)
			System.out.print(a + " ");
		System.out.println("\n");
		
		System.out.println("��������Ҫ��ľ���һ��������" + count + "�����к����к���������ɵ�������\n\n");
	}
}