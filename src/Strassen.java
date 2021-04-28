import java.util.Random;
import java.util.Scanner;

public class Strassen {
    void init_Matrix(int [][]A){//행렬을 1~5의 랜덤한 숫자로 채움
        Random r = new Random();
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A[0].length;j++){
                A[i][j] = r.nextInt(5)+1;
            }
        }
    }
    void print_Matrix(int [][]A){//행렬 출력
        for(int i=0;i<A.length;i++){
            for(int j=0;j< A[0].length;j++){
                System.out.print(A[i][j]+" ");
            }
            System.out.println(" ");
        }
    }
    int[][] sum_Matrix(int [][]A,int [][]B){//행렬 덧셈
        int [][] summat = new int[A.length][A.length];
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A.length;j++){
                summat[i][j] = A[i][j]+B[i][j];
            }
        }
        return summat;
    }

    int[][] sub_Matrix(int [][]A,int [][]B){//행렬 뺼셈
        int [][] submat = new int[A.length][A.length];
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A.length;j++){
                submat[i][j] = A[i][j]-B[i][j];
            }
        }
        return submat;
    }

    int[][] mul_Matrix(int [][]A,int [][]B){//행렬의 일반 곱셈
        if(A[0].length != B.length) {//첫번쨰 행렬의 가로길이와 두번쨰 행렬의 세로길이가 같지않으면 행렬계산을 할수 없으므로
            System.out.println("곱하려는 두 행렬의 길이가 맞지 않습니다.");
            return A;
        }
        int [][] mulmat = new int[A.length][B[0].length];
        for(int i=0;i<A.length;i++){
            for(int j=0;j<B[0].length;j++){
                for(int k=0;k<A[0].length;k++){
                    mulmat[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return mulmat;
    }
    int [][] strassen_Matrix(int [][]A,int [][]B){//행렬의 슈트라센 곱
        //입력 곱셈을 처리할 두 행렬 A와B
        //출력 행렬A와 행렬B의 곱
        if(A[0].length != B.length) {//첫번쨰 행렬의 가로길이와 두번쨰 행렬의 세로길이가 같지않으면 행렬계산을 할수 없으므로
            System.out.println("곱하려는 두 행렬의 길이가 맞지 않습니다.");
            return A;
        }
        double Axl = baselog(A[0].length,2);
        double Ayl = baselog(A.length,2);
        double Bxl = baselog(B[0].length,2);
        double Byl = baselog(B.length,2);
        int cutA=0;
        int cutB=0;
        if((Axl%1) != 0 || (Ayl%1) != 0 || (Bxl%1)!= 0 || (Byl%1) != 0){
            //행렬 A와 B의 크기를 모두 A와B의 가로,세로 넓이 중 가장 큰수의 가장 가까운 2의 제곱으로 바꾸어
            //두 행렬의 크기를 모두 2^n인 정사각형으로 바꾸고 그 늘어난 공간에는 0으로 채운다.
            int cnt=0;
            int maxlength;
            maxlength = Math.max(Math.max(A.length,A[0].length),Math.max(B.length,B[0].length));
            int m = maxlength;
            while(baselog(m,2)%1 != 0){
                cnt++;
                m++;
            }
            cutA = maxlength-A.length+cnt;
            cutB = maxlength-B[0].length+cnt;
            A = merge0_Matrix(A,maxlength-A[0].length+cnt,maxlength-A.length+cnt);
            B = merge0_Matrix(B,maxlength-B[0].length+cnt,maxlength-B.length+cnt);
        }
        if(A.length <= 2){
            return mul_Matrix(A,B);
        }
        int n = A.length/2;
        int [][]A00 = cut_Matrix(0,0,A.length/2,A.length/2,A);
        int [][]A01 = cut_Matrix(n,0,A[0].length,A.length/2,A);
        int [][]A10 = cut_Matrix(0,n,A[0].length/2,A.length,A);
        int [][]A11 = cut_Matrix(n,n,A[0].length,A.length,A);
        int [][]B00 = cut_Matrix(0,0,B[0].length/2,B.length/2,B);
        int [][]B01 = cut_Matrix(n,0,B[0].length,B.length/2,B);
        int [][]B10 = cut_Matrix(0,n,B[0].length/2,B.length,B);
        int [][]B11 = cut_Matrix(n,n,B[0].length,B.length,B);

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
        return cut_Matrix(0,0,B[0].length-cutB,A.length-cutA,merge_Matrix(C00,C01,C10,C11));
    }
    int [][]cut_Matrix(int startx,int starty,int endx,int endy,int [][]A){

        int [][]cutmat = new int[endy-starty][endx-startx];
        for(int j=0,y=starty;j<endy-starty;j++,y++){
            for(int i=0,x=startx;i<endx-startx;i++,x++){
                cutmat[j][i] = A[y][x];
            }
        }
         return cutmat;
    }

    int [][]merge_Matrix(int [][]C00,int [][]C01,int [][]C10,int [][]C11){//C00,C01,C10,C11을 각각 좌상,우상,좌하,우하에두어 헙쳐서 하나의 행렬로
        int[][] mermat = new int[C00.length+C11.length][C00[0].length+C11[0].length];
        for(int i=0;i<C00.length;i++){
            for(int j=0;j<C00[0].length;j++)mermat[i][j] = C00[i][j];
        }
        for(int i=0;i<C01.length;i++){
            for(int j=0;j<C01[0].length;j++)mermat[i][j+C00[0].length] = C01[i][j];
        }
        for(int i=0;i<C10.length;i++){
            for(int j=0;j<C10[0].length;j++)mermat[i+C00.length][j] = C10[i][j];
        }
        for(int i=0;i<C11.length;i++){
            for(int j=0;j<C11[0].length;j++)mermat[i+C00.length][j+C00[0].length] = C11[i][j];
        }
        return mermat;
    }

    double baselog(int x,double base){//밑이 base인 log함수
        return Math.log10(x)/Math.log10(base);
    }

    int [][]merge0_Matrix(int [][]A,int x,int y){//x,y만큼 넓이를 더한 행렬의 나머지부분을 0으로 채운 함수
        int[][] z01 = new int[A.length][x];
        int[][] z10 = new int[y][A[0].length];
        int[][] z11 = new int[y][x];
        return merge_Matrix(A,z01,z10,z11);
    }

    public static void main(String[] args) {
        Strassen str = new Strassen();
        Scanner sc = new Scanner(System.in);
        System.out.println("행렬 A의 가로길이 입력");
        int Ax = sc.nextInt();
        System.out.println("행렬 A의 세로길이 입력");
        int Ay = sc.nextInt();
        System.out.println("행렬 B의 가로길이 입력");
        int Bx = sc.nextInt();
        System.out.println("행렬 B의 세로길이 입력");
        int By = sc.nextInt();

        int [][] A = new int[Ay][Ax];
        int [][] B = new int[By][Bx];
        System.out.println(A.length);
        str.init_Matrix(A);//배열A를 1~5의 랜덤한 숫자로 채움
        str.init_Matrix(B);//배열B를 1~5의 랜덤한 숫자로 채움
        System.out.println("A배열");
        str.print_Matrix(A);
        System.out.println("B배열");
        str.print_Matrix(B);

        System.out.println("일반적인 행렬 곱");
        long normal_start = System.currentTimeMillis();
        str.print_Matrix(str.mul_Matrix(A,B));
        long normal_end = System.currentTimeMillis();
        System.out.println("일반 곱의 실행시간: "+(normal_end-normal_start)/1000.0);

        System.out.println("슈트라센 행렬 곱");
        long strassen_start = System.currentTimeMillis();
        str.print_Matrix(str.strassen_Matrix(A,B));
        long strassen_end = System.currentTimeMillis();
        System.out.println("슈트라센 곱의 실행시간: "+(strassen_end-strassen_start)/1000.0);
    }
}