# Strassen-Algorithm

## 서론

- 행렬의 곱셈을 분할-정복(Divide-Conquer)알고리즘의 하나인 슈트라센 알고리즘을 이용하여 계산하고    

  일반적인 행렬 곱셈과 비교한다.

## 목차

1. 행렬의 곱
2. 코드
   - 코드 구조
   - 개선 과정
   - 실행 방법
3. 시간복잡도 비교
4. 실행결과

---------------

### 1. 행렬의 곱

행렬 a와 b를 곱할때 일반적인 행렬의 곱셈 방법은 아래와 같다.

![일반적인 행렬의 곱](https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fssl.pstatic.net%2Fimages.se2%2Fsmedit%2F2015%2F10%2F7%2Fifgkdfr2qjsuqs.jpg%22&type=w2)

하지만 슈트라센 알고리즘은 특이한 방식을 사용한다.     

행렬a,b를 4부분으로 나누어서 알맞게 조합하여 새로운 행렬m1~m7을 만든다.

![슈트라센](http://yimoyimo.tk/images/strassen3.png)

이렇게 만들어진 m1~m7을 조합하여 행렬C 를 구성하는데 위 결과값을 계산하면 n=2인 일반적인행렬의 곱의 결과와 같다.

![](https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fssl.pstatic.net%2Fimages.se2%2Fsmedit%2F2015%2F10%2F7%2Fifgm9ch32b5ok9.jpg%22&type=w2){: width="70%" height="70%"}



이러한 슈트라센 알고리즘을 n이 4이상인 행렬에 적용시킬땐 분할-정복을 이용하여 각 행렬을 4부분으로 나눈 후(분할,Divide)  각부분을 슈트라센 곱을 이용하여 행렬곱을 완성시키고(정복,Conquer) 각 부분을 합쳐 완성한다.

### 2.코드(개선점 추가)

- 코드구조

  슈트라센 행렬곱을 실행시키면 우선 첫번쨰 행렬의 가로의 길이와 두번쨰 행렬의 세로길이가 같은지 검사하고 맞지않으면 메세지를 출력한 후 첫번째 행렬을 리턴한다.

  ```java
  if(A[0].length != B.length) {//첫번쨰 행렬의 가로길이와 두번쨰 행렬의 세로길이가 같지않으면 행렬계산을 할수 없으므로
              System.out.println("곱하려는 두 행렬의 길이가 맞지 않습니다.");
              return A;
          }
  ```

  그리고 행렬의 길이가  2보다 작다면 더이상 슈트라센 행렬곱이 불가능 하므로 일반적인 행렬곱으로 계산한다.

  ```java
  if(A.length <= 2){
              return mul_Matrix(A,B);
          }
  ```

  ```java
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
  ```

  

  위 조건에 모두 맞지 않는다면 행렬A와 B를 각각 4부분으로 나누고 각 부분들을 조합하여 M1~M7을 만드는데 이 과정에서도 행렬간의 곱셈이 있으니 재귀함수를 이용하여 슈트라센 행렬곱을 이용해 행렬곱을 계산한다.

  ```java
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
  ```

  ```java
  int [][]cut_Matrix(int startx,int starty,int [][]A){
  
          int [][]cutmat = new int[A.length/2][A.length/2];
          for(int i=0,x=startx;i<A.length/2;i++,x++){
              for(int j=0,y=starty;j<A.length/2;j++,y++){
                  cutmat[i][j] = A[x][y];
              }
          }
           return cutmat;
      }
  ```

  

  이렇게 만들어진 M1~M7을 이용하여 결과값인 행렬C01~C11을 만들고 각부분을 병합하여 행렬C를 리턴한다.

  ```java
  int [][]C00 = sum_Matrix(sub_Matrix(sum_Matrix(M1,M4),M5),M7);
          int [][]C01 = sum_Matrix(M3,M5);
          int [][]C10 = sum_Matrix(M2,M4);
          int [][]C11 = sum_Matrix(sum_Matrix(sub_Matrix(M1,M2),M3),M6);
          return merge_Matrix(C00,C01,C10,C11);
  ```

  ```java
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
  ```

  

- 개선 과정

  슈트라센 곱은 길이가 2^n으로 같은 두 정사각행렬만 곱이 가능하다는 단점이 있는데 이를 해결하기 위해 아래와같은 개선과정을 거쳤다.  

  우선 A와B의 가로,세로 길이를 log2의 기능을 하는 함수인 baselog()에 넣어 나온 값을 각각 Axl,Ayl,Bxl,Byl에 저장한다.

  ```java
  		double Axl = baselog(A[0].length,2);
          double Ayl = baselog(A.length,2);
          double Bxl = baselog(B[0].length,2);
          double Byl = baselog(B.length,2);
  ```

  ```java
  double baselog(int x,double base){//밑이 base인 log함수
      return Math.log10(x)/Math.log10(base);
  }
  ```

  그리고 이들중 하나라도 정수가 아니라면 기존 A,B의 길이중 가장 큰 값을 maxlength에 저장한 후 maxlength가 2^n이 될때까지 maxlength를 증가 시키고 나머지 행렬의 가로,세로길이도 같은 길이 만큼 증가시킨다. 그렇게 증가시킨 행렬의 부분은 0으로 채워진다.

  ```java
  if((Axl%1) != 0 || (Ayl%1) != 0 || (Bxl%1)!= 0 || (Byl%1) != 0){
              //행렬 A와 B의 크기를 모두 A와B의 가로,세로 넓이 중 가장 큰수의 가장 가까운 2의 제곱으로 바꾸어
              //두 행렬의 크기를 모두 2^n인 정사각형으로 바꾸고 그 늘어난 공간에는 0으로 채운다.
              int cnt=0;
              int maxlength;
              maxlength = Math.max(Math.max(A.length,A[0].length),Math.max(B.length,B[0].length));
              while(baselog(maxlength,2)%1 != 0){
                  cnt++;
                  maxlength++;
              }
              A = merge0_Matrix(A,maxlength-A[0].length+cnt,maxlength-A.length+cnt);
              B = merge0_Matrix(B,maxlength-B[0].length+cnt,maxlength-B.length+cnt);
  
          }
  ```

  ```java
  int [][]merge0_Matrix(int [][]A,int x,int y){//x,y만큼 넓이를 더한 행렬의 나머지부분을 0으로 채운 함수
      int[][] z01 = new int[A.length][x];
      int[][] z10 = new int[y][A[0].length];
      int[][] z11 = new int[y][x];
      return merge_Matrix(A,z01,z10,z11);
  }
  ```

  이 과정을 거치고 나면 두 행렬 모두 길이가 2^n인 정사각 행렬이 되고 기존 슈트라센을 이용할 수 있게된다.    

  하지만 결과 값은 일반적인 행렬의 곱의 결과와 같지 않다. 일반적인 곱의 결과에 0이 붙은 형태로 결과가 나오게 된다. 이를 해결 하기위해 0을 제거해주어야 한다.

- 실행방법

   