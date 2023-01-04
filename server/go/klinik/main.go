package main

import (
	"fmt"
	"log"
	"database/sql"
	_ "github.com/lib/pq"
	"net/http"
	"github.com/gin-gonic/gin"
	cors "github.com/rs/cors/wrapper/gin"
)

type Patient struct {
	ID 				int  	`json:"id"`
	Name  			string  `json:"name"`
	BirthDate 		string  `json:"birth_date"`
	PhoneNumber  	string 	`json:"phone_number"`
}

func main() {
	connStr := "postgres://postgres:postgres@localhost/dita_db_clinic"
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		log.Fatal(err)
	}
	
	router := gin.Default()
	router.Use(cors.Default())

	router.GET("/api/patients", func (c *gin.Context) {
		var patients []Patient
		
		rows, err := db.Query("SELECT * FROM dita_table_patients")
		if err != nil {
			log.Fatal(err)
		}
		defer rows.Close()
		
		for rows.Next() {
			var patient Patient
			err := rows.Scan(&patient.ID, &patient.Name, &patient.BirthDate, &patient.PhoneNumber);
			if err != nil {
				log.Fatal(err)
			}
			patients = append(patients, patient)
		}
		
		c.IndentedJSON(http.StatusOK, patients)
	})
	
	router.GET("/api/patients/:id", func (c *gin.Context) {
		fmt.Println("hmm")
		
		ID := c.Param("id")
		var patient Patient
		
		row := db.QueryRow("SELECT * FROM dita_table_patients WHERE id = $1", ID)
		
		err := row.Scan(&patient.ID, &patient.Name, &patient.BirthDate, &patient.PhoneNumber); 
		if err != nil {
			if err == sql.ErrNoRows {
				c.IndentedJSON(http.StatusNotFound, gin.H{"message": "patient not found"})
			}
			c.IndentedJSON(http.StatusNotFound, gin.H{"message": "patient not found"})
			log.Fatal(err)
		}
		
		c.IndentedJSON(http.StatusOK, patient)
		
	})

	router.Run("localhost:8081")
	
}