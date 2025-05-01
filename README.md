# OOP Practice Suite
The practice project is a Java 23 application developed for a class assignment, 
demonstrating object-oriented programming concepts through three independent management systems: 
Land Management, Mission Management, and Nursery Management. Each system is implemented in its 
own package (landMgtSystem, missionMgtSystem, nurseryMgtSystem) with a main class 
(LandMain, MMain, NMain) that can be executed separately.

## Usage
Run the image with the following commands to execute each system:

```
docker run -it <image-name> package-name.main class name
Example: docker run -it <image-name> landMgtSystem.LandMain
```
## Notes
- Requires Docker to be installed.
- No external dependencies; built for Java 23.
- Designed for academic evaluation, providing a simple way to test each management system.
