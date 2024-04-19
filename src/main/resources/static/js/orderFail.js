

function redirectToProductToOrderPageWithError(error) {
    console.error("주문 생성 중 오류가 발생했습니다:", error);
    // 실패 페이지에서 이전 페이지의 정보를 추출합니다.
    const previousPageInfo = window.location.search;
    // 실패 페이지에서 주문하기 페이지로 이동하면서 이전 페이지의 정보를 전달합니다.
    window.location.href = `/order/productToOrder.html${previousPageInfo}`;
}
