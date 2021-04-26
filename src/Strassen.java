import java.util.Random;
import java.util.Scanner;

public class Strassen {
    void init_Matrix(int n,int [][]A){
        Random r = new Random();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                A[i][j] = r.nextInt(5)+1;
            }
        }
    }
    void print_Matrix(int [][]A){
        for(int i=0;i<A.length;i++){
            for(int j=0;j< A.length;j++){
                System.out.print(A[i][j]+" ");
            }
            System.out.println(" ");
        }
    }
    int[][] sum_Matrix(int [][]A,int [][]B){
        int [][] summat = new int[A.length][A.length];
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A.length;j++){
                summat[i][j] = A[i][j]+B[i][j];
            }
        }
        return summat;
    }
    int[][] sub_Matrix(int [][]A,int [][]B){
        int [][] submat = new int[A.length][A.length];
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A.length;j++){
                submat[i][j] = A[i][j]-B[i][j];
            }
        }
        return submat;
    }
    int[][] mul_Matrix(int [][]A,int [][]B){
        int [][] mulmat = new int[A.length][A.length];
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A.length;j++){
                for(int k=0;k<A.length;k++){
                    mulmat[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return mulmat;
    }
    int [][] strassen_Matrix(int [][]A,int [][]B){
        if(A.length <= 2){
            return mul_Matrix(A,B);
        }
        int n = A.length/2;
        int [][]A00 = cut_Matrix(0,0,A);
        int [][]A01 = cut_Matrix(0,n,A);
        int [][]A10 = cut_Matrix(n,0,A);
        int [][]A11 = cut_Matrix(n,n,A);
        int [][]B00 = cut_Matrix(0,0,B);
        int [][]B01 = cut_Matrix(0,n,B);
        int [][]B10 = cut_Matrix(n,0,B);
        int [][]B11 = cut_Matrix(n,n,B);

        int[][] M1 = strassen_Matrix(sum_Matrix(A00,A11),sum_Matrix(B00,B11));
        int[][] M2 = strassen_Matrix(sum_Matrix(A10,A11),B00);
        int[][] M3 = strassen_Matrix(A00,sub_Matrix(B01,B11));
        int[][] M4 = strassen_Matrix(A11,sub_Matrix(B10,B00));
        int[][] M5 = strassen_Matrix(sum_Matrix(A00,A01),B11);
        int[][] M6 = strassen_Matrix(sub_Matrix(A10,A00),sum_Matrix(B00,B01));
        int[][] M7 = strassen_Matrix(sub_Matrix(A01,A11),sum_Matrix(B10,B11));

        int [][]C00 = sum_Matrix(sub_Matrix(sum_Matrix(M1,M4),M5),M7);
        int [][]C01 = sum_Matrix(M3,M5);
        int [][]C10 = sum_Matrix(M2,M4);
        int [][]C11 = sum_Matrix(sum_Matrix(sub_Matrix(M1,M2),M3),M6);
        return merge_Matrix(C00,C01,C10,C11);
    }
    int [][]cut_Matrix(int startx,int starty,int [][]A){

        int [][]cutmat = new int[A.length/2][A.length/2];
        for(int i=0,x=startx;i<A.length/2;i++,x++){
            for(int j=0,y=starty;j<A.length/2;j++,y++){
                cutmat[i][j] = A[x][y];
            }
        }
         return cutmat;
    }

    int [][]merge_Matrix(int [][]C00,int [][]C01,int [][]C10,int [][]C11){
        int[][] mermat = new int[C00.length*2][C00.length*2];
        for(int i=0;i<C00.length;i++){
            for(int j=0;j<C00.length;j++){
                mermat[i][j] = C00[i][j];
                mermat[i][j+ C00.length] = C01[i][j];
                mermat[i+ C00.length][j] = C10[i][j];
                mermat[i+ C00.length][j+ C00.length] = C11[i][j];
            }
        }
        return mermat;
    }
    
    public static void main(String[] args) {
        Strassen str = new Strassen();
        Scanner sc = new Scanner(System.in);
        System.out.println("2의 제곱인 배열의 길이 n입력");
        int n = sc.nextInt();
        int [][] A = new int[n][n];
        int [][] B = new int[n][n];
        System.out.println(A.length);
        str.init_Matrix(n,A);
        str.init_Matrix(n,B);
        System.out.println("A배열");
        str.print_Matrix(A);
        System.out.println("B배열");
        str.print_Matrix(B);
        System.out.println("일반적인 행렬 곱");
        str.print_Matrix(str.mul_Matrix(A,B));

        System.out.println("슈트라센");
        str.print_Matrix(str.strassen_Matrix(A,B));






    }
}
