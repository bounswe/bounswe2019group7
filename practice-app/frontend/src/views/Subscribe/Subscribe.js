import React, { Component } from 'react';

import axios from 'axios';

class Subscribe extends Component {
  constructor(props) {
    super(props);

    this.state = {
      subMail: '',
      unsubMail: '',
    };
  }

  baseUrl = "http://3.86.94.89:8000/";

  subscribe = (e) => {
      e.preventDefault();
      console.log("Subscribing "+this.state.subMail);

      axios.post(this.baseUrl+"subscribe/",{email: this.state.subMail})
      .then(()=>{
        alert("Thanks for subscribing!");
      })
      .catch(()=>{
        alert("There was an error subscribing, please try again later");
      })
  }

  componentDidMount() {
    window.open(this.baseUrl+"mail/");
  }

  handleChange = (e) => {
      this.setState({ [e.target.name] : e.target.value });
  }

  render() {

    return (<div/>
    );
  }
}

export default Subscribe;
