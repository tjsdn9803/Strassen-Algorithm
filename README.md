# Strassen-Algorithm

## 서론

- 행렬의 곱셈을 분할-정복(Divide-Conquer)알고리즘의 하나인 슈트라센 알고리즘을 이용하여 계산하고    

  일반적인 행렬 곱셈과 비교한다.

## 목차

1. 행렬의 곱
2. 코드
   - 코드 구조
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

이렇게 만들어진 m1~m7을 조합하여 행렬C 를 구성하는데 위 결과값을 계산하면 

![](https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fssl.pstatic.net%2Fimages.se2%2Fsmedit%2F2015%2F10%2F7%2Fifgm9ch32b5ok9.jpg%22&type=w2){: width="70%" height="70%"}

로써 행렬의 크기가 2일때 일반적인 행렬의 곱을했을 떄와 결과가 같아진다.



