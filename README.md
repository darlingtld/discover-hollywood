# discover-hollywood
Discover-hollywood help people navigate and explore movies

# Project structure
Discover-hollywood is a web application.  People can sign up to search their loved movies and give rates to other movies. The system will recommend to users their possibly interested movies based on their rating history.

Three main parts of the system:
1.  Search
2.  Recommend
3.  Social(TODO)

For backend development, Java is used as the main programming language.  SpringMVC is chosen to be the main framework to build this system.  Several datasets are provied by GroupLens.org, which is movies, links, tags and ratings.  These four are sort of relational objects.  So I used MySQL database to store these data.  Further input can easily be inserted or appended into the database.
