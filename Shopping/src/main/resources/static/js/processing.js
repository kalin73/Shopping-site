const backendLocation = 'http://localhost:8080'

let categoryId = document.getElementById("categoryId").getAttribute("value");
let productSection = document.getElementById("products");


fetch("http://localhost:8080/api/product/" + categoryId)
    .then(response => response.json())
    .then(element => {
        element.forEach(product => {
            let page = `<div class="productContent">
					  <a href="/products/info/${product.id}"><img src="${product.image}"/></a>
					  <h3>${product.productName}</h3>
					  <p>Price: ${product.price}.лв<p>
					  <div class="stars">
						<i class="fas fa-star"></i>
						<i class="fas fa-star"></i>
						<i class="fas fa-star"></i>
						<i class="fas fa-star"></i>
						<i class="fas fa-star"></i>
					  </div>
					  <a class = "btn" href="/add/${product.id}">Add to cart</a>
				  </div>`;

            productSection.innerHTML += page;
        })
    })