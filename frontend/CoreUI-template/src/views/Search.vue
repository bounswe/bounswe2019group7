<template>

    <form class="form-inline">

        <div class="form-group" id="bar-div">

            <label for="input" class="sr-only"></label>

            <input type="text" class="form-control" id="bar" placeholder="Search">

         </div>

        <b-button type="submit" variant="primary" v-on:click="search">Search</b-button>

    </form>

</template>

  
<script>
    export default {
      name: "Search",
      data: function() {
        return {
          errors: [],
          isDisabled: false
        };
      },

      methods: {
        search: function(event) {
            var textToSearch = document.getElementById("bar").value;
            //console.log(textToSearch);

            //TODO functionality
            return '';
        },
      }
    };
</script>

<style>
    #bar-div{
      display: inline-block;
      margin-top: 0%;
      margin-left: 5%;
    }

    #bar{
      display: inline-block;
      
    }
</style>
  
