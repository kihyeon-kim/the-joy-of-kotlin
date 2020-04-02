# ch2. 코틀린 프로그래밍의 개요

- 필드 및 변수 선언과 초기화하기
- 코틀린 클래스와 인터페이스
- 두 종류의 코틀린 컬렉션
- 함수(그리고 제어 구조)
- 널(null) 다루기


## 2.16 변성: 파라미터화한 타입과 하위 타입

- 변성(variance)은 파라미터화한 타입이 서로 어떤 하위 타입 관계에 있는지 결정하는 방식을 뜻한다
- 공변성(covariance)은 Red 가 Color 하위 타입일 때 Matcher<Red>가 Matcher<Color>의 하위 타입이라는 뜻이다
  - 이런 경우 Matcher<T>는 타입 파라미터 T에 대해 공변성이라고 말한다
  - 반대로 Red 가 Color 의 하위 타입일 때 Matcher<Color>가 Matcher<Red>의 하위 타입이라면 Matcher<T>는 타입 파라미터 T 에 대해 반공변성(contravariant)이라고 말한다
- List<String>. String 은 Any 의 하위 타입이므로 List<String>을 List<Any>의 하위 타입으로 간주해도 된다는 사실이 명백하다
