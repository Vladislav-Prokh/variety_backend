package com.onlineShop.store.services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.onlineShop.store.Exceptions.UserHasAlreadyRateThisProduct;
import com.onlineShop.store.Exceptions.UserHasntBoughtCurrentProductException;
import com.onlineShop.store.Interfaces.Option;
import com.onlineShop.store.ProductFactory.ProductFactory;
import com.onlineShop.store.ProductFactory.ProductFactoryProvider;
import com.onlineShop.store.dto.ProductDTO;
import com.onlineShop.store.entities.Product;
import com.onlineShop.store.entities.ProductImage;
import com.onlineShop.store.entities.ProductRating;
import com.onlineShop.store.entities.Subcategory;
import com.onlineShop.store.entities.User;
import com.onlineShop.store.repositories.OrderedProductRepository;
import com.onlineShop.store.repositories.ProductImageRepository;
import com.onlineShop.store.repositories.ProductRatingRepository;
import com.onlineShop.store.repositories.ProductRepository;


@Service
@Transactional
public class ProductService {
	
	@Autowired
	private ProductRepository productRep;
	@Autowired
	private ProductRatingRepository ratingRep;
	@Autowired
	 @Lazy
	private CommentService commentService;
	@Autowired
	private ProductImageRepository imageRep;
	@Autowired
	private SubcategoryService subcategoryService;
	@Autowired
	private OrderedProductRepository orderedProductRep;
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserUtils userUtils;

	@Autowired
	private ProductFactoryProvider productFactoryProvider;
	
	public Long addProduct(ProductDTO ProductDto,Integer subcategoryId) {
	    Map<String, Object> additionalFields = ProductDto.getAdditionalFields();
	    Subcategory subcategoryForCurrentProduct = subcategoryService.findById((long)subcategoryId).orElse(null); 
	    Product commonProduct  = ProductDto.getProduct();
	  
	    ProductFactory productFactory = productFactoryProvider.getProductFactory(subcategoryForCurrentProduct);
	    Product specificProduct = productFactory.createProduct();
	    
	    BeanUtils.copyProperties(commonProduct, specificProduct);

	    specificProduct.setSubcategory(subcategoryForCurrentProduct);
	    productFactory.initializeAdditionalFields(specificProduct, additionalFields);
	    
	    Long id = this.productRep.save(specificProduct).getId();
	    return id;
	}
	@Transactional
	public void deleteProductById(Long id) {
		this.orderedProductRep.deleteByProductId(id);
		this.commentService.deleteAllCommentsByProductId(id);
	    this.ratingRep.deleteByProductId(id);
	    this.imageRep.deleteByProductId(id);
		this.productRep.deleteById(id);
	}
	public void deleteProductByName(String name) {
		this.productRep.deleteProductByProductName(name);
	}
	public Product findProductById(Long id) {
		return productRep.findById(id).get();
	}
	public List<Option> findAllProducts(){
		 List<Product> products = productRep.findAll();
		 List<Option> options = new ArrayList<>();
		 options.addAll(products);
		 return options;
	}
	public void updateProductById(Long id) {
		
		
	}
	public Option getProductWithCorrectTypeById(Long id) {
		Optional<Product> optionalProduct = productRep.findById(id);
		 if (optionalProduct.isPresent()) {
			 	Subcategory subcategoryByProduct = optionalProduct.get().getSubcategory();
			    ProductFactory productFactory = productFactoryProvider.getProductFactory(subcategoryByProduct);
			    Product specificProduct = productFactory.getProductFromBdById(id);
			    return specificProduct;
		   }
		 else {
			return new Product();
		 }
	}
	public List<ProductRating> getRatingsProductByProductId(long productId) {
		Product product = productRep.findById(productId).get();
		if(product!= null) {
			return ratingRep.findAllByProduct(product);
		}
		return List.of();
	}
	public void setRatingToProdyctByProductIdAnnUserId(Long productId,String userEmail , int rate) {
		boolean hasUserBoughtThisProduct = userUtils.checkIfUserByEmailBoughtProductById(productId, userEmail);
	    if(!hasUserBoughtThisProduct) {
	    	throw new UserHasntBoughtCurrentProductException("User has not bought this product");
	    }
		Product productToRate = productRep.findById(productId).orElseThrow(NoSuchElementException::new);
		List<ProductRating> productsRatings = ratingRep.findAllByProduct(productToRate);
		boolean isUserRated = productsRatings.stream().anyMatch(productRating -> productRating.getUser().getEmail().equals(userEmail));
		if(isUserRated) {
			throw new UserHasAlreadyRateThisProduct("User has already rate this product");
		}
		else {
			User user = userService.findUserByEmail(userEmail);
			ProductRating rating = ProductRating.builder().product(productToRate).rating(rate).user(user).build();
			ratingRep.save(rating);
		}	
	}
	public List<Product> findByName(String name) {
	    List<Product> products = productRep.findByProductNameContainingIgnoreCase(name);
	    return products != null ? products : new ArrayList<>();
	}
	public void uploadImages(List<MultipartFile> files,Integer product_id,Integer mainImageIndex) {
		
		List<ProductImage> imagesToSave = new ArrayList<ProductImage>();
      	Product productForPhoto = productRep.findById((long)product_id).get();
		int imageIndex = 0;
		
		for(MultipartFile file : files) {
			 try {
	                String base64File = convertFileToBase64(file);
	                String apiKey = System.getenv("ImageServiceUploadAPIKey");
	                String url ="https://api.imgbb.com/1/upload?key="+apiKey;
	             
	                RestTemplate restTemplate = new RestTemplate();
	                
	                HttpHeaders headers = new HttpHeaders();
	                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	                
	                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	                body.add("image", base64File);
	                
	                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
	                
	                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
	
	                System.out.println(responseEntity);
	                HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
	             
	                if (statusCode.is2xxSuccessful()) {
	                	 String responseBody = responseEntity.getBody();
	                	 ProductImage currentImage = new ProductImage();
	                	 JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
	                	 String deleteUrl = jsonObject.getAsJsonObject("data").get("delete_url").getAsString();
	                	 currentImage.setDeleteImageURL(deleteUrl);
	                	 JsonObject dataObject = jsonObject.getAsJsonObject("data");
	                 	 String displayUrl = dataObject.get("display_url").getAsString();
	                	 if(imageIndex == mainImageIndex) {
	                	   	 productForPhoto.setDisplayImageURL(displayUrl);
	                	 }
	                	 currentImage.setDisplayImageURL(displayUrl);
	                	 currentImage.setProduct(productForPhoto);
	                	 imagesToSave.add(currentImage);
	                	 imageIndex++;
	                } else {
	                    System.err.println("Error: " + statusCode);
	                }
	                
	            } 
			 catch (Exception e) {
	                e.printStackTrace();
	            }
		
		}
		 imageRep.saveAll(imagesToSave);
	}
	
	
    private static String convertFileToBase64(MultipartFile file) throws Exception {
        byte[] fileBytes = file.getBytes();
        return Base64.getEncoder().encodeToString(fileBytes);
    }
    public List<String> getUniqueBrands(int limit) {
    	List<String> brands = productRep.findDistinctBrandsIgnoreCase();
    	if (brands.size() > limit) {
    	    brands = brands.subList(0, limit);
    	}
    	else {
    		int endIndex = Math.min(brands.size(), 10);
    		brands = brands.subList(0, endIndex);
    	}
       return brands;
    }
	public void updateProduct(Product newProduct) {
		Product productFromDb = productRep.findById(newProduct.getId()).get();
		 productFromDb.setProductName(newProduct.getProductName());
	        productFromDb.setDescription(newProduct.getDescription());
	        productFromDb.setPrice(newProduct.getPrice());
	        productFromDb.setBrand(newProduct.getBrand());
	        productFromDb.setColors(newProduct.getColors());
	        productRep.save(productFromDb);
	}
	@Transactional
	public boolean deleteImageFromProduct(Integer productId, Integer imageId) {
	    Product product = productRep.findById((long) productId).orElseThrow(() -> new RuntimeException("Product not found"));
	    
	    List<ProductImage> images = product.getImages();

	    ProductImage imageToRemove = images.stream()
                .filter(image -> image.getId().equals((long)imageId))
                .findFirst()
                .orElse(null);
	    boolean removed = images.remove(imageToRemove);
	    if (imageToRemove == null) {
	    	return false;
	    }
	    if (removed) {
	        productRep.saveAndFlush(product);
	        return true; 
	    } else {
	        return false; 
	    }
	}
}
