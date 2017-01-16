/**
 * Created by elisabethmesoy on 12.01.2017.
 */

$(document).ready(function () {

    angular.module('sortApp', [])

        .controller('mainController', function($scope) {
            $scope.sortType = 'published';
            $scope.sortReverse = false;
            $scope.searchEmlpoyee = '';
            $scope.employee = [{
                name: 'Kristin Weiseth',
                position: 'Sykepleier',
                percent: 100,
                phone: 12345678,
                hours: 37.5,
                mail: 'kristin@hotmail.com'
            }, {
                name: 'Stine Sandvold Øien',
                position: 'Helsefagarbeider',
                percent: 75,
                phone: 23456789,
                hours: 25,
                mail: 'stine@hotmail.com'
            }, {
                name: 'Elisabeth Nygård Mesøy',
                position: 'Assistent',
                percent: 50,
                phone: 34567890,
                hours: 17,
                mail: 'elisabeth@hotmail.com'
            }, ];
        });
});



