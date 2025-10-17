# 🩸 Blood Transfusion Center - Blood Bank Management System

## 📋 Description

A Java EE web application for complete management of a blood transfusion center. The system automates the matching process between donors and recipients based on blood type compatibility and medical urgency.

## 🎯 Project Goals

Solve the challenges of manual management of blood centers by providing a modern web solution that allows:

* Automated management of donors and recipients
* Intelligent matching based on blood compatibility
* Automatic prioritization based on medical urgency
* Full tracking of the donation process

## ✨ Key Features

### 👥 Donor Management

* **Creation & Registration**: Forms with automatic validation
* **Personal Information**: Full name, CIN, phone, birth date, weight, gender, blood type
* **Availability Status**:

    * `AVAILABLE`: Can donate blood
    * `NOT_AVAILABLE`: Already associated with a recipient
    * `NOT_ELIGIBLE`: Does not meet eligibility criteria
* **Automatic Eligibility Checks**:

    * Age between 18 and 65
    * Minimum weight 50 kg
    * No medical contraindications (Hepatitis B/C, HIV, insulin-dependent diabetes, pregnancy, breastfeeding)
* **Business Rules**:

    * 1 donor = 1 blood bag maximum
    * 1 donor associated with only 1 recipient at a time
    * Status automatically updated after association

### 🏥 Recipient Management

* **Creation & Registration**: Forms with automatic validation
* **Personal Information**: Full name, CIN, phone, birth date, gender, blood type
* **Urgency Levels**:

    * `CRITICAL`: Needs 4 blood bags
    * `URGENT`: Needs 3 blood bags
    * `NORMAL`: Needs 1 blood bag
* **Recipient Status**:

    * `WAITING`: Waiting for donors
    * `SATISFIED`: Received required number of blood bags
* **Automatic Prioritization**: Sorted descending (CRITICAL → URGENT → NORMAL)

### 🔗 Association System

* **Automatic Blood Compatibility**: Based on standard compatibility matrix

    * O-: Universal donor
    * AB+: Universal recipient
    * Strict compliance with compatibility rules
* **Smart Filtering**:

    * For a recipient: Only show compatible and available donors
    * For a donor: Only show compatible and unsatisfied recipients
* **Real-Time Validation**: Automatic business rule checks

### 📊 Lists & Visualization

* **Donor List**:

    * Table with full information
    * Associated recipients
    * Actions: Edit/Delete
    * Status indicators
* **Recipient List**:

    * Table with full information
    * Associated donors
    * Actions: Edit/Delete
    * Urgency and status indicators
    * Automatic priority sorting

## 🏗️ Technical Architecture

### Technology Stack

* **Language**: Java 8+
* **Frontend**: JSP + JSTL + CSS
* **Backend**: Java Servlets
* **Build Tool**: Maven
* **Server**: Apache Tomcat
* **Database**: MySQL/PostgreSQL
* **ORM**: JPA/Hibernate
* **Testing**: JUnit 5

### Multi-Layer MVC Architecture

```
├── Presentation Layer (JSP/JSTL)
│   └── User Views
├── Controller Layer (Servlets)
│   ├── DonorServlet
│   ├── RecipientServlet
│   └── AssociationServlet
├── Service Layer (Business Logic)
│   ├── DonorService
│   ├── RecipientService
│   ├── AssociationService
│   ├── CompatibilityService
│   └── EligibilityService
├── DAO Layer (Data Access)
│   ├── DonorDao
│   ├── RecipientDao
│   └── GenericDao
└── Model Layer (JPA Entities)
    ├── Donor
    ├── Recipient
    └── Person
```

### Design Patterns Used

* **Repository Pattern**: Data access
* **Singleton Pattern**: Factories and utilities
* **Factory Pattern**: DaoFactory for DAO creation
* **MVC Pattern**: Clear separation of concerns
* **Generic DAO**: Reusable data access code

### SOLID Principles

* **Single Responsibility**: Each class has one responsibility
* **Open/Closed**: Extend functionality without modifying existing code
* **Liskov Substitution**: Use interfaces for DAOs
* **Interface Segregation**: Specific and coherent interfaces
* **Dependency Inversion**: Depend on abstractions

## 📁 Project Structure

```
BloodTransfusionCenter/
├── src/
│   ├── main/
│   │   ├── java/com/bloodtransfusioncenter/
│   │   │   ├── controller/    # Servlets
│   │   │   ├── service/       # Business logic
│   │   │   ├── dao/           # Data access
│   │   │   ├── model/         # JPA entities
│   │   │   ├── enums/         # Enums
│   │   │   └── util/          # Utilities
│   │   ├── resources/
│   │   │   └── META-INF/persistence.xml
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml
│   │       │   └── views/
│   │       └── assets/        # CSS, JS, Images
│   └── test/
│       ├── java/
│       └── resources/META-INF/persistence-test.xml
├── pom.xml
└── README.md
```

## 🚀 Installation & Deployment

### Prerequisites

* JDK 8+
* Apache Maven 3.6+
* Apache Tomcat 9+
* MySQL 8.0+ or PostgreSQL 12+
* Java IDE (IntelliJ IDEA, Eclipse, NetBeans)

### Database Setup

1. Create the database:

```sql
CREATE DATABASE blood_transfusion_center;
```

2. Configure `persistence.xml`:

```xml
<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/blood_transfusion_center"/>
<property name="javax.persistence.jdbc.user" value="your_user"/>
<property name="javax.persistence.jdbc.password" value="your_password"/>
```

### Build & Compile

```bash
# Navigate to project
cd BloodTransfusionCenter

# Compile the project
mvn clean compile

# Run tests
mvn test

# Generate WAR
mvn clean package
```

### Deploy on Tomcat

**Option 1: Using IDE**

* Configure Tomcat in IDE
* Deploy project directly

**Option 2: Manual Deployment**

```bash
# Copy WAR to Tomcat
cp target/blood-transfusion-center.war $TOMCAT_HOME/webapps/

# Start Tomcat
$TOMCAT_HOME/bin/startup.sh

# Access the application:
# http://localhost:8080/blood-transfusion-center/
```

## 🧪 Testing

### Enum Tests

* `BloodTypeTest`: Blood compatibility tests
* `OtherEnumsTest`: Other enum tests

### Service Tests

* `CompatibilityServiceTest`: Compatibility logic
* `EligibilityServiceTest`: Eligibility criteria
* `AssociationServiceTest`: Association process

### DAO Tests

* `DonorDaoTest`: CRUD operations on donors
* `RecipientDaoTest`: CRUD operations on recipients

### Run Tests

```bash
# Run all tests
mvn test

# Specific tests
mvn test -Dtest=BloodTypeTest
mvn test -Dtest=CompatibilityServiceTest
```

## 📊 Blood Compatibility Matrix

| Recipient | Compatible Donors |
| --------- | ----------------- |
| O-        | O-                |
| O+        | O-, O+            |
| A-        | O-, A-            |
| A+        | O-, O+, A-, A+    |
| B-        | O-, B-            |
| B+        | O-, O+, B-, B+    |
| AB-       | O-, A-, B-, AB-   |
| AB+       | All               |

## 🔒 Validation Rules

### Donor Validation

* Age: 18-65
* Weight: ≥ 50 kg
* No medical contraindications
* Unique and valid CIN
* Valid phone number
* Consistent birth date

### Recipient Validation

* Complete personal info
* Unique and valid CIN
* Valid phone number
* Consistent birth date
* Defined urgency level

### Association Validation

* Blood compatibility
* Donor available
* Recipient not satisfied
* No duplicates

## 🛠️ Technologies & Dependencies

### Main Maven Dependencies

```xml
<!-- JPA/Hibernate -->
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>5.6.15.Final</version>
</dependency>

<!-- MySQL/PostgreSQL Driver -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>

<!-- Servlet API -->
<dependency>
    <groupId>javax.servlet</
```


groupId> <artifactId>javax.servlet-api</artifactId> <version>4.0.1</version> </dependency>

<!-- JSTL -->

<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>

<!-- JUnit -->

<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.3</version>
    <scope>test</scope>
</dependency>
```

## 📝 Application Pages

### 1. Creation Page (`create.jsp`)

* Separate forms for donor/recipient
* Automatic field validation
* Dynamic error messages
* Automatic eligibility calculation

### 2. Donor List (`donor-list.jsp`)

* Table with full information
* Associated recipients
* Actions: Edit/Delete/Associate
* Visual status indicators
* Filtering and search

### 3. Recipient List (`recipient-list.jsp`)

* Table sorted by priority
* Associated donors
* Actions: Edit/Delete/Associate
* Urgency indicators
* Progress of needs

### 4. Association Form (`association/form.jsp`)

* Smart selection based on compatibility
* Real-time validation
* Confirmation before association

## 🎨 User Interface

* Responsive modern design
* User feedback messages
* Visual status indicators
* Intuitive navigation
* Error handling pages (404.jsp, 500.jsp)

## 📈 Project Management

### Scrum Methodology with JIRA

* Detailed user stories
* Organized sprints
* Prioritized backlog
* Task tracking

### Deliverables

* ✅ Complete source code (Maven structure)
* ✅ Deployable WAR file
* ✅ README.md (full documentation)
* ✅ JIRA link
* ✅ Class diagram
* ✅ Unit tests (minimum 2)

## 🔄 Java Stream & Collections

The project uses heavily:

* **Stream API** for filtering and transformations
* **Optional** for null handling
* **Collections** for data manipulation
* **Lambda Expressions** for concise code

## 🚧 Optional Features

* [ ] Integration tests
* [ ] Automatic reactivation of donors after 1 month
* [ ] Pagination for lists
* [ ] Dynamic search bar
* [ ] Advanced filtering
* [ ] Dockerization
* [ ] Analytics dashboard with Chart.js
* [ ] Manual WAR deployment

## 🐛 Error Handling

* Custom error pages (404, 500)
* Explicit error messages
* Server-side validation
* Logging with SLF4J/Logback
* Managed JPA transactions

## 👨‍💻 Author

**Moustapha Amdy**

* Individual project - JEE Blood Transfusion Center
* Date: October 2025

## 📄 License

Academic project - All rights reserved
