const backendLocation = 'http://localhost:8080/'
let productId = document.getElementById("productId").getAttribute("value");
let counter = 0;

let placeholder = document.getElementById('information');
let videoSection = document.getElementById('videoId');

fetch(backendLocation + "api/product/info/" + productId)
	.then(response => response.json())
	.then(productInfo => {

		let out = "";
		let vid = "";

		out += `<tr>
				<th id = "pictures"><img src="${productInfo.image}" /></th>
				<th id = "productName"><stong>${productInfo.productName}</strong>
					</br>
					Цена: <span id = "productPrice">${productInfo.price} лв.</span><a href="/add/${productInfo.id}" class="btn">Add Card</a>
				</th>
			</tr>`

		for (const element of productInfo.specs) {
			if (counter % 2 === 0) {
				out += `<tr id = "color">
					<td>${element.name}</th>
					<td>${element.value}</th>
				</tr>
			</table>`
			}
			else {
				out += `<tr>
					<td>${element.name}</th>
					<td>${element.value}</th>
				</tr>`
			}

			counter++;
		}
		if (productInfo.video != null) {
			vid += `<iframe id="frame"
					src= https://www.youtube.com/embed/${productInfo.video}> 
				</iframe>`

			
		}
		
		placeholder.innerHTML = out;
		videoSection.innerHTML = vid;
	})