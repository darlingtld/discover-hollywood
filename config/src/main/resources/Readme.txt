1. install mysql
    use hollywoood.sql to initialize the database
    configuration file is located at movie.properties

2. localhost:8080/app/druid
    open database monitoring stuff provided by druid

3. install mongodb
    refer to http://www.mongoing.com/downloads

    create a database named 'hollywood'

    mongodump -h dbhost -d dbname -o dbdirectory

    mongorestore --host localhost -d hollywood --dir 'xxxxxx' --drop
    
    start mongodb: mongod --dbpath d:\Mongodbdata

4. run the db_init files to initialize the database