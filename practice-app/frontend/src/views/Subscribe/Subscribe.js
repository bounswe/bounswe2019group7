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

  baseUrl = "http://localhost:8000/";

  subscribe = () => {
      console.log("Subscribing "+this.state.subMail);
      axios.post(this.baseUrl+"subscribe/",{email: this.state.subMail})
      .then(()=>{
        alert("Thanks for subscribing!");
      })
      .catch(()=>{
        alert("There was an error subscribing, please try again later");
      })
  }

  handleChange = (e) => {
      this.setState({ [e.target.name] : e.target.value });
  }

  render() {

    return (
      <div className="animated fadeIn">
        <Row>
          <Col>
            <Card>
                <CardHeader>
                    <i className="fa fa-envelope"></i><strong>Subscribe to Mail List</strong>
                </CardHeader>
                <CardBody>
                    <Form>
                       <Row form>
                         <Col md="2">
                            <Label>Email</Label>
                         </Col>
                         <Col md="8">
                            <Input placeholder="abc@xyz.com" name="subMail" value={this.state.subMail} onChange={this.handleChange}/>
                        </Col>
                        <Col md="2">
                            <Button onClick={this.subscribe} color="primary">Subscribe</Button>
                         </Col>
                        </Row>
                    </Form>
                </CardBody>
            </Card>
          </Col>
        </Row>
      </div>
    );
  }
}

export default Subscribe;
