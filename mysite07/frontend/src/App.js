import React from 'react';
import './assets/scss/App.scss';
import {BrowserRouter as Router} from 'react-router-dom';
import {Routes, Route} from 'react-router';
import Main from './component/main';
import Gallery from './component/gallery';
import Guestbook from './component/guestbook';
import SignIn from './component/user/SignIn';
import SignUp from './component/user/SignUp';
import Settings from './component/user/Settings';
import Error404 from './component/error/Error404';

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path={'/'} element={<Main />}/>
                <Route path={'gallery'} element={<Gallery />}/>
                <Route path={'guestbook'} element={<Guestbook />}/>
                <Route path={'login'} element={<SignIn />}/>
                <Route path={'signup'} element={<SignUp />}/>
                <Route path={'settings'} element={<Settings />}/>
                <Route path={"*"} element={<Error404 />} />
            </Routes>
        </Router>
    );
}