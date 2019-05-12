from django.shortcuts import render
from django.http import HttpResponse
from addToMailList.forms import EmailSignupForm
from addToMailList.models import Signup
# Create your views here.
form = EmailSignupForm()

# handles request, shows index.html at /mail page.
def index(request):
    if request.method == "POST":
        email = request.POST["email"]
        new_signup = Signup()
        new_signup.email = email
        new_signup.save()

    context = {
        'form': form
    }
    return render(request, 'index.html', context)


