package com.onlineShop.store.ProductFactory;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.onlineShop.store.entities.Subcategory;
@Component
public class ProductFactoryProvider {
    @Autowired
    private ApplicationContext applicationContext;

    public ProductFactory getProductFactory(Subcategory subcategory) {
        switch (subcategory.getName()) {
            case "parfums":
                return applicationContext.getBean(ParfumeFactory.class);
            case "smartphones":
                return applicationContext.getBean(SmartphoneFactory.class);
            case "laptops":
                return applicationContext.getBean(LaptopFactory.class);
            case "pc":
                return applicationContext.getBean(PcFactory.class);
            case "men clothes":
            case "women clothes":
            case "child clothes":
                return applicationContext.getBean(ClothesItemFactory.class);
            case "accessories":
                return applicationContext.getBean(AccessoryFactory.class);
            default:
                return applicationContext.getBean(DefaultFactory.class);
        }
    }
}
