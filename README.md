# discover-hollywood
Discover-hollywood help people navigate and explore movies
for demo: http://210.71.198.101:8080/app
you can register yourself to find it out or you can use a test account "lingda/123"

# Project structure
Discover-hollywood is a web application.  People can sign up to search their loved movies and give rates to other movies. The system will recommend to users their possibly interested movies based on their rating history.

Three main parts of the system:
1.  Search
2.  Recommend
3.  Socialize(TODO)

# Backend
For backend development, Java is used as the main programming language.  SpringMVC is chosen to be the main framework to build this system.  Several datasets are provied by GroupLens.org, which is movies, links, tags and ratings.  These four are sort of relational objects.  So I used MySQL database to store these data.  Further input can easily be inserted or appended into the database.
Hibernate is used to do the object relational mapping for movies, links, tags, ratings and other tables.  Together with hibernate, in this project, I choose Alibaba Druid to serve as the database connection pool and monitoring tool.  
Users in discover-hollywood can rate movies and make friends.  For the data structure of users, a list of favourite movies, recommended movies and rated movies are linked together with one user.(relationship of friends has also been taken into consideration).  So MongoDB is the one I choose to store the data structure like users in discover-hollywood.
Spring-Data can work well with MongoDB.  Thus it serves as a dao layer of this project.  Maven is used to manage all the dependencies during the development.

## Search
Lucene is an open source project that provides full-text search engine.  So I choose lucene to build index on movies, such that users can quickly get what they want by typing something and get the results.  There is a quartz job in the backend that build index on newly-input movies.

## Recommend
Lenskit is an open source project that works as recommend engine to generate scored results based on relational datasets.  Here I used it to build data model on top of table ratings and calculate the scored items for user recommendation.  Once some user rates some movies.  The recommend engine will calculate the movies the recommend.  There is a quartz job to process the data of ratings every several minutes.  It will produce some movie list to recommend.  The generated results are stored in the MongoDB associated with the user.

## Ratings
The volume of ratings dataset is huge.  Users need to see the highest rated movies, the most rated movies and etc.  So I created another table called avgratings to store these information.  In this table, there are fields like movieId, avgRatings, sumRatings, sampleCount.  There is a quartz job to go over the original ratings data. Each time just a part of the data is being calculated and put into the table.  So it is fast to get data like highest-rated, most-rated and etc.

## Crawler
It might be a little sterile just to look at the movie titles or tags.  So I figured it might be interesting to get the poster and the brief description of the movie.  So there are two crawlers working in the backend to fetch image urls and description information of the movies.

# Frontend
For frontend development, I choose AngularJS to do the most work and Bootstrap to decorate the pages.  AngularJS is the MV* framework and it provides two-way binding between the View and the Model, reducing a lot of work.  Bootstrap is a good css library.  It minimize the effor the develop cross browsers and cross platform web pages.  Discover-hollywood is a single page application.  Page hopping just happens within some areas of the whole html.  There is no need to render the whole page and load resource again if there is just a small part of the page being changed.  Bower is used to manage all the libraries I need to use during the development.

 



