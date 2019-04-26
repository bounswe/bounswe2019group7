from django.test import TestCase

# Create your tests here.

from django.urls import reverse
from rest_framework.test import APITestCase, APIClient
from rest_framework.views import status
from .models import Portfolio
from .serializers import SongsSerializer