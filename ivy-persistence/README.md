# Persistence Utils Example

## 1. Create database connection from `Config/databases.yaml` ##

![DBConfig](_docs/images/1_DB_Config.png)
![DBConnection](_docs/images/2_DB_Connection.png)

## 2. Create persistence units from `Config/databases.yaml` ##
![DBPersistence](_docs/images/3_DB_Persistence.png)

>[!IMPORTANT]
> We need to define the persistence name in `getPersistenceUnitName()` function
>![DBPersistence](_docs/images/4_Persistence_Name.png)

## Demo ##
![Create Category](_docs/images/AddCategory.png)
![Update Product](_docs/images/EditProduct.png)