package com.onlineShop.store.contollers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.onlineShop.store.Interfaces.Option;
import com.onlineShop.store.dto.ProductDTO;
import com.onlineShop.store.entities.Product;
import com.onlineShop.store.entities.ProductRating;
import com.onlineShop.store.services.ProductService;

@RestController
@CrossOrigin(origins = "http://85.217.171.56:8081", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})

@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Long> addProduct(@RequestBody ProductDTO ProductDTO, @RequestParam("subcategoryId") Integer subcategoryId) {
		Long product_id = productService.addProduct(ProductDTO,subcategoryId);
		return ResponseEntity.ok().body(product_id);
	}
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
	public void deleteProduct(@PathVariable("id") Long id) {
		this.productService.deleteProductById(id);
	}
	@RequestMapping(value = "/id/{id}",method = RequestMethod.GET)
	public Option findProductById(@PathVariable("id")Long id) {
		return this.productService.getProductWithCorrectTypeById(id);
	}	
	@RequestMapping(value = "/admin/id/{id}",method = RequestMethod.GET)
	public Product findProductByIdForAdminPanel(@PathVariable("id")Long id) {
		return  this.productService.findProductById(id);
	}	
	@RequestMapping(value = "/getRatings/{productId}",method = RequestMethod.GET)
	public List<ProductRating> getAllRatingProductById(@PathVariable("productId")Long productId) {
		return productService.getRatingsProductByProductId(productId);
	}	
	@RequestMapping(value = "/setRatings/{productId}/{userEmail}/{rating}",method = RequestMethod.POST)
	public void getAllRatingProductById(@PathVariable("productId")Long productId,@PathVariable("userEmail")String userEmail,@PathVariable("rating")Integer rating) {
		productService.setRatingToProdyctByProductIdAnnUserId(productId, userEmail,rating);
	}
	@RequestMapping(value = "/{name}",method = RequestMethod.GET)
	public List<Product> findProductByName(@PathVariable("name")String name) {
		return productService.findByName(name);
	}
	@RequestMapping(value = "/uploadImages",method = RequestMethod.POST)
	public void handleFileUpload(@RequestParam("photos") List<MultipartFile> photos, @RequestParam("product_id") Integer product_id,@RequestParam("mainImageIndex") Integer mainImageIndex) throws IOException {
	    productService.uploadImages(photos,product_id,mainImageIndex);
	}
	
	@RequestMapping(value = "/deleteImageInProduct/{productId}/{imageId}",method = RequestMethod.POST)
	public boolean deleteImage(@PathVariable("productId") Integer productId,@PathVariable("imageId") Integer imageId) throws IOException {
	  return  productService.deleteImageFromProduct(productId,imageId);
	}
	
	
	@RequestMapping(value = "/brands/unique/{limit}",method = RequestMethod.GET)  
	public List<String> getUniqueBrands(@PathVariable("limit") Integer limit){
		return	productService.getUniqueBrands(limit);
	}
	@RequestMapping(value = "/update/{productId}",method = RequestMethod.POST)
	public void updateProductById(@RequestBody Product productToUpdate) {
		 productService.updateProduct(productToUpdate);
	}
}
