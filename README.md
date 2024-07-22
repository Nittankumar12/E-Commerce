# Prometheus and Grafana Setup for Spring Boot Applications

## 1. Add Prometheus Dependency

Add the following dependency to your `pom.xml` to include Prometheus metrics support in your Spring Boot application:

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>

2. Create Prometheus Configuration File
Create a file named prometheus.yml in the src/main/resources directory of your project with the following content:

yaml
# Global configuration
global:
  scrape_interval:     15s  # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s  # Evaluate rules every 15 seconds. Default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
# - "first_rules.yml"
# - "second_rules.yml"

# Scrape configuration
scrape_configs:
  # Scrape Prometheus itself
  - job_name: 'prometheus'
    static_configs:
      - targets: ['127.0.0.1:8765']

  # Scrape Spring Boot actuator endpoint
  - job_name: 'spring-actuator'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 5s
    static_configs:
      - targets: ['192.168.31.1:8765']  # Replace with the actual IP and port of your Spring Boot application


3. Verify Actuator Endpoint
Ensure that your Spring Boot application is exposing the actuator endpoints:

Access Actuator: http://localhost:8765/actuator
Check Prometheus Metrics: http://localhost:8765/actuator/prometheus
4. Set Up Prometheus
Pull Prometheus Docker Image
Run the following command to pull the Prometheus image:

bash
docker pull prom/prometheus
Run Prometheus with Configuration File
Execute the following command to run Prometheus with the configuration file:

bash
docker run -p 8765:8765 -v C:\Users\nittan.kumar\E-Commerce\api-gateway\src\main\resources\prometheus.yml:/prometheus.yml prom/prometheus
Ensure the path to prometheus.yml is correct and replace it if necessary.
Access Prometheus Web UI
You can access Prometheus by navigating to:

http://localhost:9090/ (if running locally)
http://<your-ip>:9090/ (replace <your-ip> with your actual IP address)
5. Set Up Grafana
Pull Grafana Docker Image
Run the following command to pull the Grafana image:

bash
docker run -d --name=grafana -p 3000:3000 grafana/grafana
Access Grafana Web UI
Open Grafana in your browser:

http://localhost:3000/ (if running locally)
http://<your-ip>:3000/ (replace <your-ip> with your actual IP address)
Configure Grafana
Sign in to Grafana using the default credentials (admin/admin).
Add Prometheus as a data source:
Go to Configuration > Data Sources > Add data source.
Choose Prometheus and provide the URL: http://host.docker.internal:9090/ or http://<your-ip>:9090/.
Save and test the configuration.
Create Dashboards
Create and configure dashboards in Grafana to visualize the metrics from Prometheus.

Additional Information
Ensure that your Spring Boot application is properly configured to expose metrics at /actuator/prometheus.
Adjust the targets in prometheus.yml to match the IP and port of your Spring Boot application.
Use Grafana's features to create custom dashboards and alerts based on the collected metrics.
