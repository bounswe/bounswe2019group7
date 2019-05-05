"""api_project URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.conf.urls import url
from django.urls import path
from addToMailList.views import email_list_signup
from mail.views import index


urlpatterns = [
    path('admin/', admin.site.urls),
    path('articles/', include('Article.urls')),
    path('currencies/', include('Currency.urls')),
    url(r'^subscribe/$', email_list_signup, name='email-list-signup'),
    url(r'^mail/', index, name='index'),
]
