# Cart JUnit Demo

A small Java 17 / Maven project that models a shopping cart and is covered by unit tests
written with JUnit 5 and Mockito.

## What is tested

| Class | Test class | What the tests cover |
|---|---|---|
| `ShoppingCart`, `Product` | `ShoppingCartTest` | adding, merging and removing items, totals, input validation, exceptions |
| `DiscountService` | `DiscountServiceTest` | discount codes (parameterized test), no-code case, invalid code, custom exception |
| `OrderService` | `OrderServiceTest` | checkout flow with a mocked `PaymentGateway` (Mockito): success, empty cart, failed payment |

## Run the tests

```
mvn test
```

Requires JDK 17+ and Maven 3.8+.

## JUnit and Mockito features used

`@Test`, `@BeforeEach`, `@DisplayName`, `assertEquals`, `assertThrows`, `assertTrue`/`assertFalse`,
`@ParameterizedTest` with `@CsvSource`, `@ExtendWith(MockitoExtension.class)`, `@Mock`, `when(...).thenReturn(...)`,
`verify(...)`, `never()`.
