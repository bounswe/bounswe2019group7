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
  Progress,
  Row,
  Table,
  ListGroup,
  ListGroupItem,
  Form,
  FormGroup,
  FormText,
  Input,
  Label,
} from 'reactstrap';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities'

const axios = require('axios')
const baseUrl = "http://127.0.0.1:8000/"

class HomePage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      quantityDollars: 0,
      quantityTL: 0,
      parities: []
    };

  }

  componentDidMount(){
   // axios.get(baseUrl+'currencies/').then(function (response) {console.log(response); this.state.parities=response})
  }

  handleChange = (e) => {
    this.setState({[e.target.name] : e.target.value})
  }

  convertToDollars(){
    var dollars = this.state.quantityDollars
    // Do api request
  }

  convertToTL(){
    var tl = this.state.quantityTL
    // Do api request
  }


  render() {

    return (
      <div className="animated fadeIn">
        <Row>
          <Col sm="12" xl="6">
            <Card>
              <CardHeader>
                <i className="fa fa-align-justify"></i><strong>Currencies</strong>
              </CardHeader>
              <CardBody>
                <ListGroup>
                  <ListGroupItem>Currency1</ListGroupItem>
                  {this.state.parities.map((value, index) => {
                    return <ListGroupItem key={index}>{value}</ListGroupItem>
                  })}
                </ListGroup>
              </CardBody>
            </Card>
          </Col>

          <Col xs="12" md="6">
            <Card>
              <CardHeader>
                <strong>Dollars to Turkish Liras</strong>
              </CardHeader>
              <CardBody>
                <Form action="" method="post" className="form-horizontal">
                  <FormGroup row>
                    <Col md="2">
                      <Label htmlFor="hf-email">Dollars:</Label>
                    </Col>
                    <Col md="4">
                      <Input placeholder="Enter Quantity" name="quantityDollars" value={this.state.quantityDollars} onChange={this.handleChange} />
                      <FormText className="help-block">Please enter the quantity</FormText>
                    </Col>
                    <Col md="3">
                      <Button type="button" onClick={this.corvertToDollars} size="sm" color="primary"><i className="fa fa-dot-circle-o"></i> Convert</Button>
                    </Col>
                    <Col md="3">
                      <h3> 10 TL </h3>
                    </Col>
                  </FormGroup>
                </Form>
              </CardBody>
            </Card>

            <Card>
              <CardHeader>
                <strong>Turkish Liras to Dollars</strong>
              </CardHeader>
              <CardBody>
                <Form action="" method="post" className="form-horizontal">
                  <FormGroup row>
                    <Col md="2">
                      <Label htmlFor="hf-email">TL:</Label>
                    </Col>
                    <Col md="4">
                      <Input placeholder="Enter Quantity" name="quantityTL" value={this.state.quantityTL} onChange={this.handleChange} />
                      <FormText className="help-block">Please enter the quantity</FormText>
                    </Col>
                    <Col md="3">
                      <Button type="button" onClick={this.corvertToTL} size="sm" color="primary"><i className="fa fa-dot-circle-o"></i> Convert</Button>
                    </Col>
                    <Col md="3">
                      <h3> 1.5 Dollars </h3>
                    </Col>
                  </FormGroup>
                </Form>
              </CardBody>
            </Card>
          </Col>

        </Row>
      </div>
    );
  }
}

export default HomePage;
