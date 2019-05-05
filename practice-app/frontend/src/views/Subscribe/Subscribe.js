import React, { Component, lazy, Suspense } from 'react';
import { Bar, Line } from 'react-chartjs-2';
import {
  Badge,
  Button,
  ButtonDropdown,
  ButtonGroup,
  ButtonToolbar,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  CardTitle,
  Col,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  DropdownToggle,
  Form,
  FormGroup,
  Progress,
  Row,
  Input,
  Table,
  Label,
  ListGroup,
  ListGroupItem
} from 'reactstrap';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities'

import axios from 'axios';

class Subscribe extends Component {
  constructor(props) {
    super(props);

    this.state = {
      subMail: '',
      unsubMail: '',
    };
  }

  baseUrl = "http://127.0.0.1:8000/";

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
