read -p "Please provide the path to import CSV to mongodb: " folder
read -p "Please provide the database to which the csv should be imported: " database
read -p "Please provide the collection to which the csv should be imported: " collection
echo "#############################"
echo "Folder being imported: $folder"
echo "Database: $database"
echo "Collection: $collection"
echo "#############################"
for FILE in $folder; do
	echo "Importing " $FILE
	mongoimport --db=$database --collection=$collection --type=csv --headerline --file=$FILE
done