import { Pagination } from "/static/js/pagination/pagination.js";

let pagination;
let productCnt = await getProductCount();

async function getProductCount() {
    try {
        const response = await axios.get('/products/api/productCount');
        return response.data;
    } catch (error) {
        console.error(error);
        return -1; // or throw an error, or return a default value
    }
}

async function getProductId() {
    try {
        const response = await axios.get('/products/api/productId');
        return response.data;
    } catch (error) {
        console.error(error);
        return -1; // or throw an error, or return a default value
    }

}

function callProductTable(page, size) {
    return axios.get('/products/lists', {
        params: {
            page: page,  // 페이지 번호
            size: size  // 페이지 크기
        }
    }) // 백엔드 API URL
        .then(function (response) {
            const productDtos = response.data.content;
            const tbody = document.getElementById('product-card');
            tbody.innerHTML = ''; // 초기화.
            let length = productDtos.length;
            for (let i = 0; i < length; i++) {
                const productDto = productDtos[i];
                const divCard = document.createElement('div');
                divCard.className = 'col mb-5';
                divCard.innerHTML = `
                    <div class="card h-100">
                        <a href="#" class="product-link" data-product-id="${productDto.id}">
                            <!-- Product image-->
                            <img class="card-img-top" src=${productDto.productImg} alt="..." />
                            <!-- Product details-->
                            <div class="card-body p-4">
                                <div class="text-center">
                                    <!-- Product name-->
                                    <h5 class="fw-bolder">${productDto.productName}</h5>
                                    <!-- Product price-->
                                    ${productDto.productPrice}
                                </div>
                            </div>
                        </a>
                        <!-- Product actions-->
                        <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                            <div class="text-center"><a class="btn btn-outline-warning mt-auto" href="#">장바구니 담기</a></div>
                        </div>
                    </div>
                `;
                tbody.appendChild(divCard);
            }
        })
        .catch(function (error) {
            console.error(error);
        });
}

function pageClickEvent(event) {
    event.preventDefault();
    let pageText = event.target.textContent;
    let page = Number(pageText);
    if (!isNaN(page)) {
        pagination.currentPage = page;
    } else if (pageText === '«') {
        if (pagination.currentPage > 1) {
            pagination.currentPage = pagination.currentPage - 1;
        }
    } else if (pageText === '»') {
        if (pagination.currentPage < pagination.totalPage) {
            pagination.currentPage = pagination.currentPage + 1;
        }
    }
    pagination.renderPagination(pagination.currentPage);
    callProductTable(pagination.currentPage - 1, pagination.dataPerPage);
}

async function renderPage() {
    productCnt = await getProductCount();
    pagination = new Pagination(6, 5, Math.ceil(productCnt / 10), pageClickEvent);
    pagination.renderPagination(1);
    await callProductTable(0, 6);

    // 상품 링크에 클릭 이벤트 핸들러 추가
    const productLinks = document.querySelectorAll('.product-link');
    productLinks.forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault();
            const productId = event.currentTarget.dataset.productId;
            productClickHandler(productId);
        });
    });
}

renderPage();

// 상품 클릭 이벤트 핸들러
function productClickHandler(productId) {
    window.location.href = `/product/productdetail.html?productId=${productId}`;
}
