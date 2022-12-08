// Listen to serach bar, call functions when user types into search bars 
document.getElementById("myInput").addEventListener("keyup",tableSearch);
document.getElementById("myInputCompany").addEventListener("keyup",CompanySearch);

/**
 * Sorts the HTML table
 * @param {HTMLTableElement} table to sort 
 * @param {number} colum the index of the col to sort 
 * @param {boolean} determines if sorting will be ascending order 
 * https://www.youtube.com/watch?v=8SL_hM1a0yo reference YT video 
 */
function sortByColumn(table, colum, asc = true) {

   //check if ascending
   const dirModifier = asc ? 1: -1; 
   // select the first table body block in HTML file
   const tBody = table.tBodies[0];
   //selects every table row, gets an array of tr instead of node list 
   const rows = Array.from(tBody.querySelectorAll("tr")); 

   //sort each row 
   const sortedRow = rows.sort((a,b) => {

      // string sorting (question name and companies)
      if (colum == 1 || colum == 4) {

         const aColText = a.querySelector(`td:nth-child(${colum + 1})`).textContent.trim();
         const bColText = b.querySelector(`td:nth-child(${colum + 1})`).textContent.trim();

         return aColText > bColText ? (1 * dirModifier) : (-1 * dirModifier);

      } // close if

      //  difficulty sorting
      else if (colum == 2) {

         const aColText = a.querySelector(`td:nth-child(${colum + 1})`).textContent.trim();
         const bColText = b.querySelector(`td:nth-child(${colum + 1})`).textContent.trim();

         // same difficulties
         if (aColText.localeCompare(bColText) == 0)
            return -1 * dirModifier;

         // a is easy but b is not easy
         if (aColText.localeCompare("Easy") == 0)
            return -1 * dirModifier;

         // a is hard
         if (aColText.localeCompare("Hard") == 0)
            return 1 * dirModifier;

         // a is medium and b is easy
         if (aColText.localeCompare("Medium") == 0 && bColText.localeCompare("Easy") == 0)
            return 1 * dirModifier;

         // a is medium and b is hard
         else (aColText.localeCompare("Medium") == 0 && bColText.localeCompare("Hard") == 0)
            return -1 * dirModifier;

      } // close else if

      // question number sorting
      else if (colum == 0){

         const aColPrice = parseFloat(a.querySelector(`td:nth-child(${colum + 1})`).textContent);
         const bColPrice = parseFloat(b.querySelector(`td:nth-child(${colum + 1})`).textContent);

         return aColPrice > bColPrice ? (1 * dirModifier) : (-1 * dirModifier);
         
      } // close else if

      // like dislike sorting
      else {

         const aColPrice = a.querySelector(`td:nth-child(${colum + 1})`).textContent;
         const bColPrice = b.querySelector(`td:nth-child(${colum + 1})`).textContent;

         return aColPrice > bColPrice ? (-1 * dirModifier) : (1 * dirModifier);

      } // close else

   }); // close sortedRow

   // remove all existing TRs from the table
   while (tBody.firstChild) {
      tBody.removeChild(tBody.firstChild);
   }

   // re-add the newly sorted rows
   tBody.append(...sortedRow);

   //remember how the colum is currently sorted 
   table.querySelectorAll("th").forEach(th => th.classList.remove("th-sort-asc", "th-sort-desc")); 
   table.querySelector(`th:nth-child(${colum + 1})`).classList.toggle("th-sort-asc", asc);
   table.querySelector(`th:nth-child(${colum + 1})`).classList.toggle("th-sort-desc", !asc);

} // close sortbyCloum


// whenever a header is click sort its row
document.querySelectorAll(".table-sortable th").forEach(headerCell => {
   headerCell.addEventListener("click", () => {
      const tableElement = headerCell.parentElement.parentElement.parentElement;
      const headerIndex = Array.prototype.indexOf.call(headerCell.parentElement.children, headerCell);
      const currentIsAscending = headerCell.classList.contains("th-sort-asc");

      sortByColumn(tableElement, headerIndex, !currentIsAscending);
   });
});


// Enables search feature to find a specific question by name
// https://www.youtube.com/watch?v=eLQhybnA9hw&t=619s reference YT video 
function tableSearch() {

   let input, filter, table, tr, td, txtValue;

   // initialize variables
   input = document.getElementById("myInput");
   filter = input.value.toUpperCase();
   table = document.getElementById("ourTable");
   tr = table.getElementsByTagName("tr");

   // for each row in the table
   for (let i = 0; i < tr.length; i++) {

      // grab the table data 
      td = tr[i].getElementsByTagName("td")[1];

      if (td) {

         txtValue = td.textContent || td.innerText;
         
         if(txtValue.toUpperCase().indexOf(filter) > -1) {
            tr[i].style.display = "";
         }

         else {
            tr[i].style.display = "none";
         }
      } // close if

   } // close for

} // close tableSearch

// Enables search feature to find a specific company by name
// https://www.youtube.com/watch?v=eLQhybnA9hw&t=619s reference YT video 
function CompanySearch() {
   let input, filter, table, tr, td, txtValue;

   // initialize variables
   input = document.getElementById("myInputCompany");
   filter = input.value.toUpperCase();
   table = document.getElementById("ourTable");
   tr = table.getElementsByTagName("tr");

   // for each row in the table
   for (let i = 0; i < tr.length; i++) {

      // grab the table data
      td = tr[i].getElementsByTagName("td")[4];

      if (td) {

         txtValue = td.textContent || td.innerText;

         if(txtValue.toUpperCase().indexOf(filter) > -1) {
            tr[i].style.display = "";
         }

         else {
            tr[i].style.display = "none";
         }
      } // close if

   } // close for

} // close tableSearch